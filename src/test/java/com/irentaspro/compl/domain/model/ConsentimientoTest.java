package com.irentaspro.compl.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.irentaspro.compl.domain.model.Consentimiento;

class ConsentimientoTest {

    private final String textoValido = "Acepto los términos...";
    private final String versionValida = "v1.2";
    private final LocalDate fechaValida = LocalDate.now();
    private final UUID usuarioIdValido = UUID.randomUUID();

    @Test
    void lanzarExceptionSiTextoEsNuloOVacio() {
        // Test ROJO ❌: El constructor debe validar el 'texto'
        assertThrows(IllegalArgumentException.class, () -> {
            new Consentimiento(null, versionValida, fechaValida, usuarioIdValido, true);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Consentimiento("  ", versionValida, fechaValida, usuarioIdValido, true); // isBlank
        });
    }

    @Test
    void lanzarExceptionSiVersionEsNulaOVacia() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Consentimiento(textoValido, null, fechaValida, usuarioIdValido, true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Consentimiento(textoValido, "", fechaValida, usuarioIdValido, true); // isEmpty
        });
    }
    
    @Test
    void lanzarExceptionSiFechaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Consentimiento(textoValido, versionValida, null, usuarioIdValido, true);
        });
    }

    @Test
    void lanzarExceptionSiUsuarioIdEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Consentimiento(textoValido, versionValida, fechaValida, null, true);
        });
    }
 @Test
    void aceptarDebeCambiarEstadoYFecha() {
        // 1. Arrange
        // Creamos un consentimiento en estado 'false' y con fecha de ayer
        LocalDate fechaAyer = LocalDate.now().minusDays(1);
        Consentimiento consentimiento = new Consentimiento(
            textoValido, versionValida, fechaAyer, usuarioIdValido, false // 'aceptado' es false
        );
        assertFalse(consentimiento.isAceptado(), "Pre-condición: Debe estar 'false'");

        // 2. Act
        // Llamamos al método de negocio
        consentimiento.aceptar();

        // 3. Assert
        // Verificamos que el estado cambió a 'true'
        assertTrue(consentimiento.isAceptado(), "El estado debió cambiar a 'true'");
        // Verificamos que la fecha se actualizó a 'hoy'
        assertTrue(
            consentimiento.getFechaAceptacion().isAfter(fechaAyer), 
            "La fecha de aceptación debió actualizarse"
        );
    }
    
    @Test
    void revocarDebeCambiarEstadoAFalse() {
        Consentimiento consentimiento = new Consentimiento(
            textoValido, versionValida, fechaValida, usuarioIdValido, true // 'aceptado' es true
        );
        assertTrue(consentimiento.isAceptado(), "Pre-condición: Debe estar 'true'");

        consentimiento.revocar();

        assertFalse(consentimiento.isAceptado(), "El estado debió cambiar a 'false'");
    }

    @Test
    void debeCrearConsentimientoValido() {
        Consentimiento consentimiento = assertDoesNotThrow(() -> {
            return new Consentimiento(textoValido, versionValida, fechaValida, usuarioIdValido, true);
        });

        assertNotNull(consentimiento);
        assertNotNull(consentimiento.getId()); 
        assertEquals(textoValido, consentimiento.getTexto());
        assertEquals(versionValida, consentimiento.getVersion());
        assertEquals(usuarioIdValido, consentimiento.getUsuarioId());
        assertTrue(consentimiento.isAceptado());
    }
    
}
