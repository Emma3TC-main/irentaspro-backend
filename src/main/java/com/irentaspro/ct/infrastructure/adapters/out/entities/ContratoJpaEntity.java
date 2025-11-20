package com.irentaspro.ct.infrastructure.adapters.out.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.irentaspro.ct.infrastructure.adapters.out.jpa.FirmaDigitalEmbeddable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contratos")
@Getter
@Setter
public class ContratoJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "propiedad_id", nullable = false)
    private UUID propiedadId;

    @Column(name = "propietario_id", nullable = false)
    private UUID propietarioId;

    @Column(name = "inquilino_id", nullable = false)
    private UUID inquilinoId;

    @Column(name = "inicio")
    private LocalDate inicio;

    @Column(name = "fin")
    private LocalDate fin;

    @Column(name = "monto", precision = 19, scale = 4)
    private BigDecimal monto;

    @Column(name = "moneda", length = 3)
    private String moneda;

    @Column(name = "monto_pendiente", precision = 19, scale = 4)
    private BigDecimal montoPendiente;

    @Column(name = "estado", length = 50)
    private String estado;

    @Embedded
    private FirmaDigitalEmbeddable firma;

    // relaciones
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    private List<CuotaJpaEntity> cuotas = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    private List<ClausulaJpaEntity> clausulas = new ArrayList<>();

    public ContratoJpaEntity() {
    }

    // IMPORTANTE: mantener consistencia bidireccional
    public void addClausula(ClausulaJpaEntity c) {
        clausulas.add(c);
        c.setContrato(this);
    }

    public void addCuota(CuotaJpaEntity q) {
        cuotas.add(q);
        q.setContrato(this);
    }

}
