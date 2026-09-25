package pe.edu.upeu.PharmaBackend.service.service;

import pe.edu.upeu.PharmaBackend.dto.*;
import java.util.*;

public interface DespachoService {
    DespachoResponseDTO registrar(DespachoRequestDTO r);
    DespachoResponseDTO buscar(Long id);
    List<DespachoResponseDTO> listar();
    DespachoResponseDTO anular(Long id);
}
