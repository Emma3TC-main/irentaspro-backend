package com.irentaspro.prop.domain.services;

import java.util.UUID;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.prop.domain.model.DocumentoPropiedad;

public class StorageService implements ServiciosDominio {

    // Servicio para manejo de almacenamiento de archivos (subida, descarga,
    // eliminación) de los documentos

    public String upload(DocumentoPropiedad documento) {
        // Lógica para subir el documento a un servicio de almacenamiento externo (S3,
        // GCS, etc.)
        return "https://storage.irentaspro.com/signed/" + documento.getUrl();
    }

    public String getSignedUrl(UUID idDocumento) {
        // Generar URL firmada para acceso temporal al documento
        return "https://storage.irentaspro.com/signed/" + idDocumento.toString();
    }

    public void delete(UUID idDocumento) {
        // Lógica para eliminar el documento del servicio de almacenamiento
    }

    @Override
    public void ejecutar() {
        // Implementación genérica del contrato servicio de dominio
    }
}
