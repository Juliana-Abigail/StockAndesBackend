package pe.edu.upeu.PharmaBackend.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.upeu.PharmaBackend.enums.EstadoDespacho;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity

@Table(name = "despachos") @Getter @Setter @NoArgsConstructor @AllArgsConstructor public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;
    @Column(length = 200)
    private String observacion;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoDespacho estado;
    @Column(name = "total_unidades", nullable = false)
    private Integer totalUnidades;
    @Column(name = "monto_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;
    @OneToMany(mappedBy = "despacho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleDespacho> detalles = new ArrayList<>();
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    public void agregarDetalle(DetalleDespacho d) {
        detalles.add(d);
        d.setDespacho(this);
    }

    @PrePersist void pre() {
        if (fecha==null)fecha = LocalDateTime.now();
        if (estado==null)estado = EstadoDespacho.REGISTRADO;
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate void upd() {
        fechaModificacion = LocalDateTime.now();
    }
}
