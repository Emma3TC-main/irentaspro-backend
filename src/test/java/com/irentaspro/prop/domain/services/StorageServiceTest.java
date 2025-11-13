package com.irentaspro.prop.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.prop.domain.model.DocumentoPropiedad;
import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;

class StorageServiceTest {

    private StorageService storageService;

    private DocumentoPropiedad documentoDePrueba;

    @BeforeEach
    void setUp() {

        storageService = new StorageService();

        // Creamos un documento real para usar en los tests
        HashDocumento hash = new HashDocumento("hash123", "SHA-256");
        documentoDePrueba = new DocumentoPropiedad("Test PDF", "documentos/mi-doc.pdf", hash);
    }

    @Test
    void uploadDebeDevolverUrlFirmadaCorrecta() {

        String urlResultado = storageService.upload(documentoDePrueba);

        assertNotNull(urlResultado);
        assertTrue(
                urlResultado.contains(documentoDePrueba.getUrl()),
                "La URL devuelta no contiene la URL original del documento");
        assertTrue(urlResultado.startsWith("https://storage.irentaspro.com/signed/"));
    }

    @Test
    void getSignedUrlDebeDevolverUrlFirmadaCorrecta() {
        UUID idDocumento = UUID.randomUUID();

        String urlResultado = storageService.getSignedUrl(idDocumento);

        assertNotNull(urlResultado);
        assertTrue(
                urlResultado.contains(idDocumento.toString()),
                "La URL devuelta no contiene el UUID del documento");
        assertTrue(urlResultado.startsWith("https://storage.irentaspro.com/signed/"));
    }

    @Test
    void deleteNoDebeLanzarExcepcion() {
        UUID idDocumento = UUID.randomUUID();

        assertDoesNotThrow(() -> {
            storageService.delete(idDocumento);
        });
    }

    @Test
    void ejecutarNoDebeLanzarExcepcion() {
        
        assertDoesNotThrow(() -> {
            storageService.ejecutar();
        });
    }
    @Test
    void uploadDebeLanzarExceptionSiDocumentoEsNulo() {
        DocumentoPropiedad docNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            storageService.upload(docNulo);
        });
    }

    @Test
    void getSignedUrlDebeLanzarExceptionSiIdEsNulo() {
        UUID idNulo = null;
        assertThrows(IllegalArgumentException.class, () -> {
            storageService.getSignedUrl(idNulo);
        });
    }
}
