package com.irentaspro.common.domain.model.valueojects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.Direccion;

class DireccionTest {
 @Test
    void lanzarExceptionSiCalleEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion(null, "Miraflores", "Lima");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion("  ", "Miraflores", "Lima");
        });
    }

    @Test
    void lanzarExceptionSiDistritoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion("Av. Larco 123", null, "Lima");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion("Av. Larco 123", "", "Lima"); 
        });
    }

    @Test
    void lanzarExceptionSiProvinciaEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion("Av. Larco 123", "Miraflores", null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Direccion("Av. Larco 123", "Miraflores", " ");
        });
        
    }   

    @Test
    void debeCrearDireccionValida() {
        String calleValida = "Av. Arequipa 500";
        String distritoValido = "Lince";
        String provinciaValida = "Lima";

        Direccion direccion = assertDoesNotThrow(() -> {
            return new Direccion(calleValida, distritoValido, provinciaValida);
        });

        assertNotNull(direccion);

        assertEquals(calleValida, direccion.calle()); 
        assertEquals(distritoValido, direccion.distrito());
        assertEquals(provinciaValida, direccion.provincia());
    }
}
