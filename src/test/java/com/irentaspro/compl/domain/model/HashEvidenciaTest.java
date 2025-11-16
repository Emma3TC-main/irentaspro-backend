package com.irentaspro.compl.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.irentaspro.compl.domain.model.HashEvidencia;

class HashEvidenciaTest {
 
    @Test
    void lanzarExceptionSiValorEsNuloOVacio() {
        String algoritmoValido = "SHA-256";

        assertThrows(IllegalArgumentException.class, () -> {
            new HashEvidencia(null, algoritmoValido);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new HashEvidencia("  ", algoritmoValido); 
        });
    }
    @Test
    void lanzarExceptionSiAlgoritmoEsNuloOVacio() {
        String valorValido = "hash-de-prueba-123";

        assertThrows(IllegalArgumentException.class, () -> {
            new HashEvidencia(valorValido, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new HashEvidencia(valorValido, "");
        });
    }

    @Test
    void debeCrearHashEvidenciaValido() {
        String valorValido = "a1b2c3d4e5f6";
        String algoritmoValido = "SHA-256";

        HashEvidencia hash = assertDoesNotThrow(() -> {
            return new HashEvidencia(valorValido, algoritmoValido);
        });

        assertNotNull(hash);
        assertEquals(valorValido, hash.getValor());
        assertEquals(algoritmoValido, hash.getAlgoritmo());
    }
    
    @Test
    void toStringDebeDevolverFormatoCorrecto() {
        HashEvidencia hash = new HashEvidencia("a1b2c3d4", "MD5");
        String formatoEsperado = "MD5:a1b2c3d4";
        
        assertEquals(formatoEsperado, hash.toString());
    }
}
