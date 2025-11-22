package com.irentaspro.prop.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "propiedades")
@Getter
@Setter
public class PropiedadEntity {

    @Id
    @Column(name = "id_propiedad")
    private UUID id;

    @Column(name = "owner_id")
    private UUID ownerId; // solo lectura

    private String titulo;
    private String descripcion;

    private String calle;
    private String distrito;
    private String provincia;

    private double latitud;
    private double longitud;

    @Column(name = "precio_valor")
    private BigDecimal precio;

    private String moneda;

    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoPropiedadEntity> documentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private UsuarioEntity propietario;
}
