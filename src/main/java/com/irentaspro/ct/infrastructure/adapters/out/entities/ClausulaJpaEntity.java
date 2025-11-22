package com.irentaspro.ct.infrastructure.adapters.out.entities;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contrato_clausulas")
@Getter
@Setter
public class ClausulaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private ContratoJpaEntity contrato;

    private String tipo;

    @Column(length = 2000)
    private String descripcion;
}
