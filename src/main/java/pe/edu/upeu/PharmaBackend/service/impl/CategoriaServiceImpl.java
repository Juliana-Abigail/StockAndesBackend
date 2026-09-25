package pe.edu.upeu.PharmaBackend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.entity.Categoria;
import pe.edu.upeu.PharmaBackend.exception.*;
import pe.edu.upeu.PharmaBackend.repository.*;
import pe.edu.upeu.PharmaBackend.service.service.CategoriaService;
import java.util.*;

@Service

@Slf4j

public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository repo;
    private final ProductoRepository productos;

    public CategoriaServiceImpl(CategoriaRepository r, ProductoRepository p) {
        repo = r;
        productos = p;
    }

    private String norm(String s) {
        return s==null?null:s.trim().replaceAll("\\s+", " ");
    }

    private CategoriaResponseDTO dto(Categoria c) {
        return new CategoriaResponseDTO(c.getId(), c.getNombre(), c.getDescripcion(), c.getEstado(), c.getFechaCreacion(), c.getFechaModificacion());
    }

    public CategoriaResponseDTO create(CategoriaRequestDTO r) {
        r.setNombre(norm(r.getNombre()));
        if (repo.existsByNombreIgnoreCase(r.getNombre())) throw new ReglaNegocioException("Ya existe una categoria con ese nombre");
        Categoria c = new Categoria();
        c.setNombre(r.getNombre());
        c.setDescripcion(r.getDescripcion());
        c.setEstado(r.getEstado());
        c = repo.save(c);
        log.info("Categoria creada id = {}", c.getId());
        return dto(c);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO r) {
        Categoria c = get(id);
        r.setNombre(norm(r.getNombre()));
        if (repo.existsByNombreIgnoreCaseAndIdNot(r.getNombre(), id))throw new ReglaNegocioException("Ya existe una categoria con ese nombre");
        c.setNombre(r.getNombre());
        c.setDescripcion(r.getDescripcion());
        c.setEstado(r.getEstado());
        log.info("Categoria actualizada id = {}", id);
        return dto(repo.save(c));
    }

    public CategoriaResponseDTO read(Long id) {
        return dto(get(id));
    }

    public void delete(Long id) {
        get(id);
        if (productos.existsByCategoriaId(id))throw new ReglaNegocioException("No se puede eliminar una categoria con productos");
        repo.deleteById(id);
        log.info("Categoria eliminada id = {}", id);
    }

    public Iterable<CategoriaResponseDTO> readAll() {
        return repo.findAll().stream().map(this::dto).toList();
    }

    private Categoria get(Long id) {
        return repo.findById(id).orElseThrow(()->new RecursosNoEncontradoException("Categoria no encontrada: "+id));
    }
}
