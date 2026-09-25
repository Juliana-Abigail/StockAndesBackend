package pe.edu.upeu.PharmaBackend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.entity.*;
import pe.edu.upeu.PharmaBackend.enums.EstadoDespacho;
import pe.edu.upeu.PharmaBackend.exception.*;
import pe.edu.upeu.PharmaBackend.repository.*;
import pe.edu.upeu.PharmaBackend.service.service.DespachoService;
import java.math.*;
import java.time.*;
import java.util.*;

@Service

@Slf4j

public class DespachoServiceImpl implements DespachoService {
    private final DespachoRepository repo;
    private final AreaRepository areas;
    private final ProductoRepository productos;

    public DespachoServiceImpl(DespachoRepository r, AreaRepository a, ProductoRepository p) {
        repo = r;
        areas = a;
        productos = p;
    }

    private BigDecimal money(BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP);
    }

    private Despacho get(Long id) {
        return repo.findById(id).orElseThrow(()->new RecursosNoEncontradoException("Despacho no encontrado: "+id));
    }

    @Transactional

    public DespachoResponseDTO registrar(DespachoRequestDTO r) {
        Area a = areas.findById(r.getAreaId()).orElseThrow(()->new RecursosNoEncontradoException("Area no encontrada: "+r.getAreaId()));
        if (!Boolean.TRUE.equals(a.getEstado())) {
            log.warn("RN-01 area inactiva id = {}", a.getId());
            throw new ReglaNegocioException("Solo se puede despachar a un area activa");
        }
        Set<Long> ids = new HashSet<>();
        Despacho d = new Despacho();
        d.setArea(a);
        d.setObservacion(r.getObservacion());
        d.setFecha(LocalDateTime.now());
        d.setEstado(EstadoDespacho.REGISTRADO);
        int unidades = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleDespachoRequestDTO x:r.getDetalles()) {
            if (!ids.add(x.getProductoId())) {
                log.warn("RN-04 producto repetido id = {}", x.getProductoId());
                throw new ReglaNegocioException("Un despacho no puede repetir productos");
            }
            Producto p = productos.findById(x.getProductoId()).orElseThrow(()->new RecursosNoEncontradoException("Producto no encontrado: "+x.getProductoId()));
            if (!Boolean.TRUE.equals(p.getEstado())) {
                log.warn("RN-01 producto inactivo id = {}", p.getId());
                throw new ReglaNegocioException("Solo se pueden despachar productos activos");
            }
            if (x.getCantidad()>p.getStock()) {
                log.warn("RN-02 stock insuficiente producto = {}", p.getCodigo());
                throw new ReglaNegocioException("Stock insuficiente para "+p.getCodigo());
            }
            BigDecimal costo = money(p.getCostoUnitario());
            BigDecimal importe = money(costo.multiply(BigDecimal.valueOf(x.getCantidad())));
            DetalleDespacho dd = new DetalleDespacho();
            dd.setProducto(p);
            dd.setCantidad(x.getCantidad());
            dd.setCostoUnitario(costo);
            dd.setImporte(importe);
            d.agregarDetalle(dd);
            unidades += x.getCantidad();
            total = total.add(importe);
        }
        total = money(total);
        LocalDate hoy = LocalDate.now();
        LocalDateTime ini = hoy.withDayOfMonth(1).atStartOfDay();
        LocalDateTime fin = ini.plusMonths(1);
        BigDecimal consumido = repo.sumarMontoAreaMes(a.getId(), EstadoDespacho.REGISTRADO, ini, fin);
        if (consumido.add(total).compareTo(a.getPresupuestoMensual())>0) {
            log.warn("RN-03 presupuesto excedido area = {}", a.getCodigo());
            throw new ReglaNegocioException("El despacho supera el presupuesto mensual del area");
        }
        d.setTotalUnidades(unidades);
        d.setMontoTotal(total);
        for (DetalleDespacho dd : d.getDetalles()) {
            dd.getProducto().setStock(
                    dd.getProducto().getStock() - dd.getCantidad()
            );
        }
        d = repo.save(d);
        log.info("Despacho registrado id = {} monto = {}", d.getId(), d.getMontoTotal());
        return dto(d);
    }

    @Transactional(readOnly = true)

    public DespachoResponseDTO buscar(Long id) {
        return dto(get(id));
    }

    @Transactional(readOnly = true)

    public List<DespachoResponseDTO> listar() {
        return repo.findAll().stream().map(this::dto).toList();
    }

    @Transactional

    public DespachoResponseDTO anular(Long id) {
        Despacho d = get(id);
        if (d.getEstado()==EstadoDespacho.ANULADO)throw new ReglaNegocioException("El despacho ya esta anulado");
        d.getDetalles().forEach(x->x.getProducto().setStock(x.getProducto().getStock()+x.getCantidad()));
        d.setEstado(EstadoDespacho.ANULADO);
        log.info("Despacho anulado id = {}", id);
        return dto(d);
    }

    private DespachoResponseDTO dto(Despacho d) {
        List<DetalleDespachoResponseDTO> ds = d.getDetalles().stream().map(x->new DetalleDespachoResponseDTO(x.getId(), x.getProducto().getId(), x.getProducto().getCodigo(), x.getProducto().getNombre(), x.getCantidad(), x.getCostoUnitario(), x.getImporte())).toList();
        return new DespachoResponseDTO(d.getId(), d.getFecha(), d.getArea().getId(), d.getArea().getCodigo(), d.getArea().getNombre(), d.getObservacion(), d.getEstado(), d.getTotalUnidades(), d.getMontoTotal(), ds);
    }
}
