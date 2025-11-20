package com.irentaspro.ct.infrastructure.adapters.out.entities;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "contrato_clausulas")
public class ClausulaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    // RELACIÓN correcta con Contrato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private ContratoJpaEntity contrato;

    @Column(name = "tipo", length = 100)
    private String tipo;

    @Column(name = "descripcion", length = 2000)
    private String descripcion;

    public ClausulaJpaEntity() {
    }
}
