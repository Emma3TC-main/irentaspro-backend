package com.irentaspro.ct.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ClausulaTest {

    @Test
    void lanzarExceptionSiTipoEsNuloOVacio() {

        String descripcionValida = "El arrendatario pagará 100 USD por día de retraso.";

        assertThrows(IllegalArgumentException.class, () -> {
            new Clausula(null, descripcionValida);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Clausula("  ", descripcionValida);
        });
    }

    @Test
    void lanzarExceptionSiDescripcionEsNulaOVacia() {
        String tipoValido = "Penalidad";

        assertThrows(IllegalArgumentException.class, () -> {
            new Clausula(tipoValido, null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Clausula(tipoValido, "");
        });
    }    


    @Test
    void debeCrearClausulaValida() {
        String tipoValido = "Mantenimiento";
        String descripcionValida = "El inquilino debe pintar el inmueble cada 6 meses.";

        Clausula clausula = assertDoesNotThrow(() -> {
            return new Clausula(tipoValido, descripcionValida);
        });

        assertNotNull(clausula);
        assertEquals(tipoValido, clausula.getTipo());
        assertEquals(descripcionValida, clausula.getDescripcion());
    }

}
