package com.irentaspro.ct.infrastructure.adapters.out.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "contrato_cuotas")
public class CuotaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    // RELACIÓN correcta con Contrato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private ContratoJpaEntity contrato;

    @Column(name = "numero")
    private int numero;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "monto", precision = 19, scale = 4)
    private BigDecimal monto;

    @Column(name = "pagado")
    private boolean pagado;

    public CuotaJpaEntity() {
    }

    
}
