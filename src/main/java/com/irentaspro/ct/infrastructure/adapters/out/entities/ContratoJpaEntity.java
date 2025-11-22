package com.irentaspro.ct.infrastructure.adapters.out.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.irentaspro.ct.infrastructure.adapters.out.jpa.FirmaDigitalEmbeddable;
import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contratos")
@Getter
@Setter
public class ContratoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "propiedad_id", nullable = false)
    private UUID propiedadId;

    @Column(name = "propietario_id", nullable = false)
    private UUID propietarioId;

    @Column(name = "inquilino_id", nullable = false)
    private UUID inquilinoId;

    private LocalDate inicio;
    private LocalDate fin;

    @Column(precision = 19, scale = 4)
    private BigDecimal monto;

    private String moneda;

    @Column(name = "monto_pendiente", precision = 19, scale = 4)
    private BigDecimal montoPendiente;

    private String estado;

    @Embedded
    private FirmaDigitalEmbeddable firma;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuotaJpaEntity> cuotas = new ArrayList<>();

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClausulaJpaEntity> clausulas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", insertable = false, updatable = false)
    private UsuarioEntity propietario;

    public void addClausula(ClausulaJpaEntity c) {
        clausulas.add(c);
        c.setContrato(this);
    }

    public void addCuota(CuotaJpaEntity q) {
        cuotas.add(q);
        q.setContrato(this);
    }
}
