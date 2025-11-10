package com.irentaspro.prop.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "propiedades")
public class PropiedadEntity {
    @Id
    @Column(name = "id_propiedad")
    private UUID id;

    @Column(name = "owner_id")
    private UUID ownerId;

    private String titulo;
    private String descripcion;

    // Direccion
    private String calle;
    private String distrito;
    private String provincia;

    // Ubicacion
    @Column(name = "lat")
    private double latitud;

    @Column(name = "lon")
    private double longitud;

    // Precio
    @Column(name = "precio_valor")
    private BigDecimal precio;

    private String moneda;

    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoPropiedadEntity> documentos;

}
