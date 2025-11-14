package com.irentaspro.common.domain.model.valueojects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.irentaspro.common.domain.model.valueobjects.EstadoContrato;

class EstadoContratoTest {

    @Test
    void lanzarExceptionSiEstadoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EstadoContrato(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new EstadoContrato("  "); 
        });
    }


    @Test
    void debeCrearEstadoValido() {
        String estadoValido = "PENDIENTE";

        EstadoContrato estado = assertDoesNotThrow(() -> {
            return new EstadoContrato(estadoValido);
        });

        assertNotNull(estado);

        assertEquals(estadoValido, estado.estado()); 
    }
    
}
