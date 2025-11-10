package com.irentaspro.prop.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "documentos_propiedad")
public class DocumentoPropiedadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_documento")
    private UUID id;

    private String tipo;
    private String url;

    @Column(name = "hash_valor")
    private String hashValor;

    @Column(name = "hash_algoritmo")
    private String hashAlgoritmo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propiedad")
    private PropiedadEntity propiedad;
}
