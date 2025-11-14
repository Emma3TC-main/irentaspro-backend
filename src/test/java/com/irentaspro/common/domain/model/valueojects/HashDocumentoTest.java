package com.irentaspro.common.domain.model.valueojects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

class HashDocumentoTest {
@Test
    void lanzarExceptionSiValorNuloOVacio() {
        String algoritmoValido = "SHA-256";

        assertThrows(IllegalArgumentException.class, () -> {
            new HashDocumento(null, algoritmoValido);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new HashDocumento("  ", algoritmoValido); // .isBlank()
        });
    }

    @Test
    void lanzarExceptionSiAlgoritmoNuloOVacio() {
        String valorValido = "hash-de-prueba-123";

        assertThrows(IllegalArgumentException.class, () -> {
            new HashDocumento(valorValido, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new HashDocumento(valorValido, "");
        });
    }    

    @Test
    void debeCrearHashValido() {
        String valorValido = "a1b2c3d4e5f6";
        String algoritmoValido = "SHA-256";

        HashDocumento hash = assertDoesNotThrow(() -> {
            return new HashDocumento(valorValido, algoritmoValido);
        });

        assertNotNull(hash);

        assertEquals(valorValido, hash.valor()); 
        assertEquals(algoritmoValido, hash.algoritmo());
    }
}
