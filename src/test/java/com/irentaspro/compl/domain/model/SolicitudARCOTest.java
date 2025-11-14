package com.irentaspro.compl.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.compl.domain.model.SolicitudARCO;
import com.irentaspro.compl.domain.model.SolicitudARCO.TipoSolicitud;

class SolicitudARCOTest {

    private TipoSolicitud tipoValido;
    private LocalDate fechaValida;
    private String estadoValido;

    @BeforeEach
    void setUp() {
        tipoValido = TipoSolicitud.ACCESO;
        fechaValida = LocalDate.now().minusDays(1);
        estadoValido = "pendiente";
    }

    @Test
    void lanzarExceptionSiTipoEsNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SolicitudARCO(null, fechaValida, estadoValido, null);
        });
    }

    @Test
    void lanzarExceptionSiFechaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SolicitudARCO(tipoValido, null, estadoValido, null);
        });
    }
    
    @Test
    void lanzarExceptionSiEstadoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SolicitudARCO(tipoValido, fechaValida, null, null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new SolicitudARCO(tipoValido, fechaValida, "  ", null); 
        });
    }

    @Test
    void responderDebeLanzarExceptionSiRespuestaEsNulaOVacia() {
        SolicitudARCO solicitud = new SolicitudARCO(tipoValido, fechaValida, estadoValido, null);

        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.responder(null); 
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.responder(""); 
        });
    }

    @Test
    void debeCrearSolicitudValida() {
        SolicitudARCO solicitud = assertDoesNotThrow(() -> {
            return new SolicitudARCO(tipoValido, fechaValida, estadoValido, null);
        });

        assertNotNull(solicitud);
        assertNotNull(solicitud.getId());
        assertEquals(tipoValido, solicitud.getTipoSolicitud());
        assertEquals(fechaValida, solicitud.getFecha());
        assertEquals(estadoValido, solicitud.getEstado());
        assertNull(solicitud.getRespuesta()); 
    }

    @Test
    void registrarDebeActualizarFechaYEstado() {
        SolicitudARCO solicitud = new SolicitudARCO(tipoValido, fechaValida, "pendiente", null);

        solicitud.registrar();

        assertEquals("registrada", solicitud.getEstado());

        assertEquals(LocalDate.now(), solicitud.getFecha());
    }

    @Test
    void responderDebeActualizarRespuestaYEstado() {
        SolicitudARCO solicitud = new SolicitudARCO(tipoValido, fechaValida, "registrada", null);
        String respuestaValida = "Sus datos han sido eliminados.";
        
        solicitud.responder(respuestaValida);

        assertEquals("respondida", solicitud.getEstado());
        assertEquals(respuestaValida, solicitud.getRespuesta());
    }
}


