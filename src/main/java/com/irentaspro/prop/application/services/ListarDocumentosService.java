package com.irentaspro.prop.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.irentaspro.prop.infrastructure.persistence.entity.DocumentoMongoEntity;
import com.irentaspro.prop.infrastructure.persistence.repository.DocumentoMongoRepositorio;

@Service
public class ListarDocumentosService {

    private final DocumentoMongoRepositorio mongoRepo;

    public ListarDocumentosService(DocumentoMongoRepositorio mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    public List<DocumentoMongoEntity> ejecutar(UUID propiedadId) {
        return mongoRepo.findByPropiedadId(propiedadId);
    }
}
