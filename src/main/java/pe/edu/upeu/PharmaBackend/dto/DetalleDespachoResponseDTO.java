package pe.edu.upeu.PharmaBackend.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class DetalleDespachoResponseDTO {
    private Long id;
    private Long productoId;
    private String productoCodigo;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal importe;
}
