package pe.edu.upeu.PharmaBackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.*;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class DespachoRequestDTO {
    @NotNull
    private Long areaId;
    @Size(max = 200)
    private String observacion;
    @NotEmpty
    private List<@Valid DetalleDespachoRequestDTO> detalles;
}
