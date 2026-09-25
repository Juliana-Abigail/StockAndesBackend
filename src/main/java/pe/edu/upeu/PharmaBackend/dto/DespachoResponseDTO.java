package pe.edu.upeu.PharmaBackend.dto;

import lombok.*;
import pe.edu.upeu.PharmaBackend.enums.EstadoDespacho;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class DespachoResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long areaId;
    private String areaCodigo;
    private String areaNombre;
    private String observacion;
    private EstadoDespacho estado;
    private Integer totalUnidades;
    private BigDecimal montoTotal;
    private List<DetalleDespachoResponseDTO> detalles;
}
