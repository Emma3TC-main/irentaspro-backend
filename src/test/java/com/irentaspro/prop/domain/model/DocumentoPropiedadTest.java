package com.irentaspro.prop.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.prop.domain.model.valueobjects.HashDocumento;

class DocumentoPropiedadTest {
    private HashDocumento hashDocumentoTest;

    @BeforeEach
    void setUp(){
        hashDocumentoTest = new HashDocumento("abc123hash", "SHA-256");
    }

    @Test
    void lanzarExceptionSiTipoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentoPropiedad(null, "http://url.com/doc.pdf", hashDocumentoTest);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentoPropiedad("  ", "http://url.com/doc.pdf", hashDocumentoTest);
        });
    }

    @Test
    void lanzarExceptionSiUrlEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentoPropiedad("Titulo", null, hashDocumentoTest    );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentoPropiedad("Titulo", "", hashDocumentoTest);
        });
    }

    @Test
    void lanzarExceptionSiHashEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DocumentoPropiedad("Titulo", "http://url.com/doc.pdf", null);
        });
    }

    @Test
    void debeCrearDocumentoPropiedadValido() {
        // 1. Arrange
        String tipoValido = "Contrato PDF";
        String urlValida = "https://s3.bucket/contrato-1.pdf";

        DocumentoPropiedad doc = assertDoesNotThrow(() -> {
            return new DocumentoPropiedad(tipoValido, urlValida, hashDocumentoTest);
        });

        // 3. Assert
        assertNotNull(doc);
        assertNotNull(doc.getId()); 
        assertEquals(tipoValido, doc.getTipo());
        assertEquals(urlValida, doc.getUrl());
        assertEquals(hashDocumentoTest, doc.getHash());
        assertEquals("abc123hash", doc.getHash().getValor());
    }
}
