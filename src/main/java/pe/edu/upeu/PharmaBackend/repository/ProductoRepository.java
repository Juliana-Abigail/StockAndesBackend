package pe.edu.upeu.PharmaBackend.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.PharmaBackend.entity.Producto;
import java.util.*;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByCodigoIgnoreCase(String codigo);
    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);
    boolean existsByCategoriaId(Long id);
    List<Producto> findByCategoriaIdOrderByNombreAsc(Long id);
    @Query("select p from Producto p where (:nombre is null or lower(p.nombre) like lower(concat('%', :nombre, '%'))) and (:categoriaId is null or p.categoria.id = :categoriaId) and (:stockBajo is null or :stockBajo = false or p.stock<=p.stockMinimo)") List<Producto> buscar(@Param("nombre") String nombre, @Param("categoriaId") Long categoriaId, @Param("stockBajo") Boolean stockBajo, org.springframework.data.domain.Sort sort);
}
