package com.irentaspro.ct.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        // 1. Arrange
        String estadoValido = "BORRADOR";
        
        // 2. Act
        // Usamos assertDoesNotThrow como pediste
        EstadoContrato estado = assertDoesNotThrow(() -> {
            return new EstadoContrato(estadoValido);
        });

        // 3. Assert
        assertNotNull(estado);
        assertEquals(estadoValido, estado.getEstado());
    }

    @Test
    void esDebeSerSensibleAMayusculas() {
        EstadoContrato estado = new EstadoContrato("FIRMADO");
        
        assertTrue(estado.es("FIRMADO"));
        assertTrue(estado.es("firmado"));
    }

    @Test
    void esDebeDevolverFalseSiNoCoincide() {
        EstadoContrato estado = new EstadoContrato("FIRMADO");
        
        assertFalse(estado.es("BORRADOR")); 
        }
    
    @Test
    void esDebeDevolverFalseSiValorEsNulo() {
        EstadoContrato estado = new EstadoContrato("FIRMADO");
    
        assertFalse(estado.es(null)); 
    }
}
