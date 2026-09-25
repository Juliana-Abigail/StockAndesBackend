package pe.edu.upeu.PharmaBackend.service.service;

import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.service.generic.CrudService;
import java.util.*;

public interface ProductoService extends CrudService<ProductoRequestDTO, ProductoResponseDTO, Long> {
    List<ProductoResponseDTO> porCategoria(Long id);
    List<ProductoResponseDTO> buscar(String nombre, Long categoriaId, Boolean stockBajo, String orden, String dir);
}
