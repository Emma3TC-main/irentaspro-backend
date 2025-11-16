package com.irentaspro.bi.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ReporteTest {
 
    @Test
    void lanzarExceptionSiTipoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reporte(null, "Contenido", LocalDateTime.now());
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Reporte("  ", "Contenido", LocalDateTime.now());
        });
    } 
    
    @Test
    void lanzarExceptionSiContenidoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reporte("Tipo", null, LocalDateTime.now());
        });
    }

    @Test
    void lanzarExceptionSiFechaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Reporte("Tipo", "Contenido", null);
        });
    }

    @Test
    void crearDebeGenerarReporteConFechaActual() {
        LocalDateTime tiempoAntes = LocalDateTime.now().minusSeconds(1);

        Reporte reporte = Reporte.crear("Tipo", "Contenido");

        assertNotNull(reporte);
        assertEquals("Tipo", reporte.getTipo());
        assertEquals("Contenido", reporte.getContenido());
        
        assertNotNull(reporte.getFechaGeneracion());
        assertTrue(reporte.getFechaGeneracion().isAfter(tiempoAntes));
        assertTrue(reporte.getFechaGeneracion().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void actualizarContenidoDebeCambiarContenidoYFecha() {
        LocalDateTime fechaOriginal = LocalDateTime.now().minusDays(1);
        Reporte reporte = new Reporte("Tipo", "Contenido Antiguo", fechaOriginal);
        
        String nuevoContenido = "Contenido Nuevo";
        
        try { Thread.sleep(10); } catch (InterruptedException e) {}

        reporte.actualizarContenido(nuevoContenido);

        assertEquals(nuevoContenido, reporte.getContenido());
        assertTrue(reporte.getFechaGeneracion().isAfter(fechaOriginal));
    }
    @Test
    void actualizarContenidoLanzaExceptionSiContenidoEsNulo() {
        Reporte reporte = new Reporte("Tipo", "Contenido Antiguo", LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> {
            reporte.actualizarContenido(null);
        });
    }


    @Test
    void debeCrearReporteValido() {
        LocalDateTime fecha = LocalDateTime.now();

        Reporte reporte = assertDoesNotThrow(() -> {
            return new Reporte("Tipo Válido", "Contenido Válido", fecha);
        });

        assertNotNull(reporte);
        assertNotNull(reporte.getId()); 
        assertEquals("Tipo Válido", reporte.getTipo());
        assertEquals(fecha, reporte.getFechaGeneracion());
    }

}
