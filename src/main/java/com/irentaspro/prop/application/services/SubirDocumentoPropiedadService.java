package com.irentaspro.prop.application.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.irentaspro.prop.application.dto.DocumentoRequest;
import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;
import com.irentaspro.prop.infrastructure.persistence.entity.DocumentoMongoEntity;
import com.irentaspro.prop.infrastructure.persistence.entity.HashDocumentoEmbeddable;
import com.irentaspro.prop.infrastructure.persistence.repository.DocumentoMongoRepositorio;

@Service
public class SubirDocumentoPropiedadService {

    private final DocumentoMongoRepositorio mongoRepo;
    private final GridFsTemplate gridFsTemplate;

    public SubirDocumentoPropiedadService(
            DocumentoMongoRepositorio mongoRepo,
            GridFsTemplate gridFsTemplate) {
        this.mongoRepo = mongoRepo;
        this.gridFsTemplate = gridFsTemplate;
    }

    public String ejecutar(UUID propiedadId, DocumentoRequest request, MultipartFile file) throws IOException {

        UUID docId = UUID.randomUUID();

        // ============================================================
        // 1. Validar METADATA del hash con el Value Object
        // ============================================================
        HashDocumento hashVO = new HashDocumento(
                request.getHashValor(),
                request.getHashAlgoritmo());

        if (!hashVO.validar()) {
            throw new IllegalArgumentException(
                    "El hash enviado no cumple con la estructura esperada para SHA-256.");
        }

        // ============================================================
        // 2. Calcular hash real del archivo subido (solo SHA-256)
        // ============================================================
        String hashReal = calcularSHA256(file.getInputStream());

        // ============================================================
        // 3. Comparar hash REAL vs hash del metadata
        // ============================================================
        if (!hashReal.equalsIgnoreCase(hashVO.getValor())) {
            throw new IllegalArgumentException(
                    "El hash del archivo no coincide. El archivo podría estar corrupto o modificado.");
        }

        // ============================================================
        // 4. Guardar archivo en GridFS
        // ============================================================
        ObjectId storedFileId = gridFsTemplate.store(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                Map.of("docId", docId.toString()));

        // ============================================================
        // 5. Convertir VO → Embeddable para Mongo
        // ============================================================
        HashDocumentoEmbeddable hashEmb = new HashDocumentoEmbeddable();
        hashEmb.setValor(hashVO.getValor());
        hashEmb.setAlgoritmo(hashVO.getAlgoritmo());

        // ============================================================
        // 6. Guardar metadata en Mongo
        // ============================================================
        DocumentoMongoEntity entity = new DocumentoMongoEntity();
        entity.setId(docId);
        entity.setPropiedadId(propiedadId);
        entity.setTipo(request.getTipo());
        entity.setFilename(file.getOriginalFilename());
        entity.setUrl("/api/v1/propiedades/" + propiedadId + "/documentos/" + docId + "/archivo");
        entity.setHash(hashEmb);
        entity.setCreatedAt(LocalDateTime.now());

        mongoRepo.save(entity);

        return docId.toString();
    }

    // ============================================================
    // MÉTODO PRIVADO: calcular SHA-256 del archivo real
    // ============================================================
    private String calcularSHA256(InputStream input) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            byte[] hashBytes = digest.digest();

            // Convertir a hexadecimal
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al calcular el hash SHA-256 del archivo", e);
        }
    }
}
