package com.irentaspro.prop.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "documentos_propiedad")
public class DocumentoMongoEntity {

    @Id
    private UUID id;

    private UUID propiedadId;
    private String tipo;
    private String filename;
    private String url;

    private HashDocumentoEmbeddable hash;

    private LocalDateTime createdAt;
}
