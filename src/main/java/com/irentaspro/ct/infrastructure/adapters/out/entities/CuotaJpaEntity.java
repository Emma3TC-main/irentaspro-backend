package com.irentaspro.ct.infrastructure.adapters.out.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contrato_cuotas")
@Getter
@Setter
public class CuotaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private ContratoJpaEntity contrato;

    private int numero;
    private LocalDate fecha;

    @Column(precision = 19, scale = 4)
    private BigDecimal monto;

    private boolean pagado;
}
