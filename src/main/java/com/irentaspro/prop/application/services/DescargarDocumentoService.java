package com.irentaspro.prop.application.services;

import java.util.UUID;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.irentaspro.prop.infrastructure.persistence.entity.DocumentoMongoEntity;
import com.irentaspro.prop.infrastructure.persistence.repository.DocumentoMongoRepositorio;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class DescargarDocumentoService {

    private final GridFsTemplate gridFs;
    private final DocumentoMongoRepositorio mongoRepo;

    public DescargarDocumentoService(GridFsTemplate gridFs, DocumentoMongoRepositorio mongoRepo) {
        this.gridFs = gridFs;
        this.mongoRepo = mongoRepo;
    }

    public GridFsResource ejecutar(UUID docId) {
        DocumentoMongoEntity doc = mongoRepo.findById(docId)
                .orElseThrow(() -> new IllegalArgumentException("Documento no encontrado"));

        GridFSFile file = gridFs.find(
                Query.query(Criteria.where("metadata.docId").is(docId.toString()))).first();

        if (file == null)
            throw new IllegalArgumentException("Archivo no encontrado en GridFS");

        return gridFs.getResource(file);
    }
}
