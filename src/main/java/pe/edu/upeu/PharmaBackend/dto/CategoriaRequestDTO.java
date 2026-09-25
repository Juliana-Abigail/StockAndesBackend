package pe.edu.upeu.PharmaBackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class CategoriaRequestDTO {
    @NotBlank
    @Size(min = 3, max = 60)
    private String nombre;
    @Size(max = 200)
    private String descripcion;
    @NotNull
    private Boolean estado;
}
