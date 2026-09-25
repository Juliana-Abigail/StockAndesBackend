package pe.edu.upeu.PharmaBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.PharmaBackend.entity.Despacho;
import pe.edu.upeu.PharmaBackend.dto.ProductoDespachadoDTO;
import pe.edu.upeu.PharmaBackend.enums.EstadoDespacho;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public interface DespachoRepository extends JpaRepository<Despacho, Long> {
    @Query("select coalesce(sum(d.montoTotal), 0) from Despacho d where d.area.id = :areaId and d.estado = :estado and d.fecha>=:inicio and d.fecha<:fin") BigDecimal sumarMontoAreaMes(@Param("areaId")Long areaId, @Param("estado")EstadoDespacho estado, @Param("inicio")LocalDateTime inicio, @Param("fin")LocalDateTime fin);
    @Query("select new pe.edu.upeu.PharmaBackend.dto.ProductoDespachadoDTO(p.codigo, p.nombre, sum(dd.cantidad), sum(dd.importe)) from DetalleDespacho dd join dd.producto p join dd.despacho d where d.estado = pe.edu.upeu.PharmaBackend.enums.EstadoDespacho.REGISTRADO and d.fecha>=:inicio and d.fecha<:fin and (:categoriaId is null or p.categoria.id = :categoriaId) group by p.codigo, p.nombre order by sum(dd.cantidad) desc, p.codigo asc") List<ProductoDespachadoDTO> reporte(@Param("inicio")LocalDateTime inicio, @Param("fin")LocalDateTime fin, @Param("categoriaId")Long categoriaId);
    @Query("select count(dd) from DetalleDespacho dd where dd.producto.id = :productoId") long contarUsoProducto(@Param("productoId")Long productoId);
}
