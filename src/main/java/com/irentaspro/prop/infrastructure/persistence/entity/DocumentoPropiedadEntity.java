package com.irentaspro.prop.infrastructure.persistence.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documentos_propiedad")
@Getter
@Setter
public class DocumentoPropiedadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
