package pe.edu.upeu.PharmaBackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity

@Table(name = "areas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 4)
    private String codigo;
    @Column(nullable = false, unique = true, length = 120)
    private String nombre;
    @Column(length = 120)
    private String responsable;
    @Column(length = 150)
    private String email;
    @Column(name = "presupuesto_mensual", nullable = false, precision = 12, scale = 2)
    private BigDecimal presupuestoMensual;
    @Column(nullable = false)
    private Boolean estado = true;
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist void pre() {
        fechaCreacion = LocalDateTime.now();
        if (estado==null)estado = true;
    }

    @PreUpdate void upd() {
        fechaModificacion = LocalDateTime.now();
    }
}
