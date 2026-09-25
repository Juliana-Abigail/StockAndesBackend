package pe.edu.upeu.PharmaBackend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.entity.Area;
import pe.edu.upeu.PharmaBackend.exception.*;
import pe.edu.upeu.PharmaBackend.repository.AreaRepository;
import pe.edu.upeu.PharmaBackend.service.service.AreaService;

@Service

@Slf4j

public class AreaServiceImpl implements AreaService {
    private final AreaRepository repo;

    public AreaServiceImpl(AreaRepository r) {
        repo = r;
    }

    private Area get(Long id) {
        return repo.findById(id).orElseThrow(()->new RecursosNoEncontradoException("Area no encontrada: "+id));
    }

    private AreaResponseDTO dto(Area a) {
        return new AreaResponseDTO(a.getId(), a.getCodigo(), a.getNombre(), a.getResponsable(), a.getEmail(), a.getPresupuestoMensual(), a.getEstado(), a.getFechaCreacion(), a.getFechaModificacion());
    }

    private void set(Area a, AreaRequestDTO r) {
        a.setCodigo(r.getCodigo().trim().toUpperCase());
        a.setNombre(r.getNombre().trim());
        a.setResponsable(r.getResponsable());
        a.setEmail(r.getEmail());
        a.setPresupuestoMensual(r.getPresupuestoMensual());
        a.setEstado(r.getEstado());
    }

    public AreaResponseDTO create(AreaRequestDTO r) {
        if (repo.existsByCodigoIgnoreCase(r.getCodigo().trim()))throw new ReglaNegocioException("El codigo del area ya existe");
        if (repo.existsByNombreIgnoreCase(r.getNombre().trim()))throw new ReglaNegocioException("El nombre del area ya existe");
        Area a = new Area();
        set(a, r);
        a = repo.save(a);
        log.info("Area creada id = {}", a.getId());
        return dto(a);
    }

    public AreaResponseDTO update(Long id, AreaRequestDTO r) {
        Area a = get(id);
        if (repo.existsByCodigoIgnoreCaseAndIdNot(r.getCodigo().trim(), id))throw new ReglaNegocioException("El codigo del area ya existe");
        if (repo.existsByNombreIgnoreCaseAndIdNot(r.getNombre().trim(), id))throw new ReglaNegocioException("El nombre del area ya existe");
        set(a, r);
        log.info("Area actualizada id = {}", id);
        return dto(repo.save(a));
    }

    public AreaResponseDTO read(Long id) {
        return dto(get(id));
    }

    public void delete(Long id) {
        throw new ReglaNegocioException("Las areas no se eliminan; cambie su estado");
    }

    public Iterable<AreaResponseDTO> readAll() {
        return repo.findAll().stream().map(this::dto).toList();
    }
}
