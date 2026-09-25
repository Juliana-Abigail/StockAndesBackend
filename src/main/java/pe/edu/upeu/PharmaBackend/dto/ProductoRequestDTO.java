package pe.edu.upeu.PharmaBackend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter

@Setter @NoArgsConstructor @AllArgsConstructor public class ProductoRequestDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}-\\d{3}$", message = "El codigo debe cumplir AAA-999")
    private String codigo;
    @NotBlank
    @Size(min = 3, max = 150)
    private String nombre;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costoUnitario;
    @NotNull
    @Min(0)
    private Integer stock;
    @NotNull
    @Min(0)
    private Integer stockMinimo;
    @NotNull
    private Boolean estado;
    @NotNull
    private Long categoriaId;
}
