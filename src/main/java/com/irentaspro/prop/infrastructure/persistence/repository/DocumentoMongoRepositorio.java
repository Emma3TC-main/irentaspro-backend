package com.irentaspro.prop.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.irentaspro.prop.infrastructure.persistence.entity.DocumentoMongoEntity;

public interface DocumentoMongoRepositorio extends MongoRepository<DocumentoMongoEntity, UUID> {

    List<DocumentoMongoEntity> findByPropiedadId(UUID propiedadId);

}