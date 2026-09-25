package pe.edu.upeu.PharmaBackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class DetalleDespachoRequestDTO {
    @NotNull
    private Long productoId;
    @NotNull
    @Min(1)
    private Integer cantidad;
}
