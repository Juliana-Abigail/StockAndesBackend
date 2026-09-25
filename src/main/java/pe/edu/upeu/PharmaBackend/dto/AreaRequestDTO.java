package pe.edu.upeu.PharmaBackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class AreaRequestDTO {
    @NotBlank
    @Pattern(regexp = "^AR\\d{2}$", message = "El codigo debe cumplir AR99")
    private String codigo;
    @NotBlank
    @Size(max = 120)
    private String nombre;
    @Size(max = 120)
    private String responsable;
    @Email
    @Size(max = 150)
    private String email;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal presupuestoMensual;
    @NotNull
    private Boolean estado;
}
