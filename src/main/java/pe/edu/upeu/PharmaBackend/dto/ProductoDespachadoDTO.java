package pe.edu.upeu.PharmaBackend.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter

@Setter @NoArgsConstructor public class ProductoDespachadoDTO {
    private String codigo;
    private String producto;
    private Long unidadesDespachadas;
    private BigDecimal montoTotal;

    public ProductoDespachadoDTO(String codigo, String producto, Long unidadesDespachadas, BigDecimal montoTotal) {
        this.codigo = codigo;
        this.producto = producto;
        this.unidadesDespachadas = unidadesDespachadas;
        this.montoTotal = montoTotal;
    }
}
