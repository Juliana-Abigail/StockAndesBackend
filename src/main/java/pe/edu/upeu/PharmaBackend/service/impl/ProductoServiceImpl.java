package pe.edu.upeu.PharmaBackend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.entity.*;
import pe.edu.upeu.PharmaBackend.exception.*;
import pe.edu.upeu.PharmaBackend.repository.*;
import pe.edu.upeu.PharmaBackend.service.service.ProductoService;
import java.util.*;

@Service

@Slf4j

public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository repo;
    private final CategoriaRepository categorias;
    private final DespachoRepository despachos;

    public ProductoServiceImpl(ProductoRepository r, CategoriaRepository c, DespachoRepository d) {
        repo = r;
        categorias = c;
        despachos = d;
    }

    private Categoria cat(Long id) {
        return categorias.findById(id).orElseThrow(()->new RecursosNoEncontradoException("Categoria no encontrada: "+id));
    }

    private Producto get(Long id) {
        return repo.findById(id).orElseThrow(()->new RecursosNoEncontradoException("Producto no encontrado: "+id));
    }

    private ProductoResponseDTO dto(Producto x) {
        return new ProductoResponseDTO(x.getId(), x.getCodigo(), x.getNombre(), x.getCostoUnitario(), x.getStock(), x.getStockMinimo(), x.getEstado(), x.getCategoria().getId(), x.getCategoria().getNombre(), x.getFechaCreacion(), x.getFechaModificacion());
    }

    private void set(Producto p, ProductoRequestDTO r) {
        p.setCodigo(r.getCodigo().trim().toUpperCase());
        p.setNombre(r.getNombre().trim());
        p.setCostoUnitario(r.getCostoUnitario());
        p.setStock(r.getStock());
        p.setStockMinimo(r.getStockMinimo());
        p.setEstado(r.getEstado());
        p.setCategoria(cat(r.getCategoriaId()));
    }

    public ProductoResponseDTO create(ProductoRequestDTO r) {
        if (repo.existsByCodigoIgnoreCase(r.getCodigo().trim()))throw new ReglaNegocioException("El codigo del producto ya existe");
        Producto p = new Producto();
        set(p, r);
        p = repo.save(p);
        log.info("Producto creado id = {}", p.getId());
        return dto(p);
    }

    public ProductoResponseDTO update(Long id, ProductoRequestDTO r) {
        Producto p = get(id);
        if (repo.existsByCodigoIgnoreCaseAndIdNot(r.getCodigo().trim(), id))throw new ReglaNegocioException("El codigo del producto ya existe");
        set(p, r);
        log.info("Producto actualizado id = {}", id);
        return dto(repo.save(p));
    }

    public ProductoResponseDTO read(Long id) {
        return dto(get(id));
    }

    public void delete(Long id) {
        get(id);
        if (despachos.contarUsoProducto(id)>0)throw new ReglaNegocioException("No se puede eliminar un producto con despachos");
        repo.deleteById(id);
        log.info("Producto eliminado id = {}", id);
    }

    public Iterable<ProductoResponseDTO> readAll() {
        return repo.findAll().stream().map(this::dto).toList();
    }

    public List<ProductoResponseDTO> porCategoria(Long id) {
        cat(id);
        return repo.findByCategoriaIdOrderByNombreAsc(id).stream().map(this::dto).toList();
    }

    public List<ProductoResponseDTO> buscar(String nombre, Long categoriaId, Boolean stockBajo, String orden, String dir) {
        Set<String> ok = Set.of("nombre", "costo", "stock");
        if (!ok.contains(orden))throw new ReglaNegocioException("Campo de orden no permitido");
        String campo = orden.equals("costo")?"costoUnitario":orden;
        Sort.Direction sd = "desc".equalsIgnoreCase(dir)?Sort.Direction.DESC:Sort.Direction.ASC;
        if (!"asc".equalsIgnoreCase(dir)&&!"desc".equalsIgnoreCase(dir))throw new ReglaNegocioException("Direccion debe ser asc o desc");
        return repo.buscar(nombre==null||nombre.isBlank()?null:nombre, categoriaId, stockBajo, Sort.by(sd, campo)).stream().map(this::dto).toList();
    }
}
