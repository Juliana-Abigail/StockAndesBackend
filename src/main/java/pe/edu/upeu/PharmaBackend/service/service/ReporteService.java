package pe.edu.upeu.PharmaBackend.service.service;

import pe.edu.upeu.PharmaBackend.dto.ProductoDespachadoDTO;
import java.util.*;

public interface ReporteService {
    List<ProductoDespachadoDTO> productosDespachados(String periodo, Long categoriaId);
}
