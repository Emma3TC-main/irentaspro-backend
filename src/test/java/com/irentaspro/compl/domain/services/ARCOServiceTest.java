package com.irentaspro.compl.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.irentaspro.compl.domain.model.SolicitudARCO;
import com.irentaspro.compl.domain.model.SolicitudARCO.TipoSolicitud;

class ARCOServiceTest {

    private ARCOService arcoService;
    private SolicitudARCO solicitudDePrueba;


    @BeforeEach
    void setUp() {
        arcoService = new ARCOService();
        
        solicitudDePrueba = new SolicitudARCO(
            TipoSolicitud.RECTIFICACION,
            LocalDate.now().minusDays(1), 
            "registrada", 
            null 
        );
    }

    @Test
    void lanzarExceptionSiSolicitudEsNula() {
        String respuestaValida = "Datos actualizados.";

        assertThrows(IllegalArgumentException.class, () -> {
            arcoService.responder(null, respuestaValida);
        });
    }

    @Test
    void lanzarExceptionSiRespuestaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            arcoService.responder(solicitudDePrueba, null);
        });
    }

    @Test
    void debeRegistrarSolicitudCorrectamente() {
        TipoSolicitud tipo = TipoSolicitud.ACCESO;

        SolicitudARCO solicitud = assertDoesNotThrow(() -> {
            return arcoService.registrarSolicitud(tipo);
        });

        assertNotNull(solicitud);
        assertNotNull(solicitud.getId());
        assertEquals(tipo, solicitud.getTipoSolicitud());
        assertEquals("registrada", solicitud.getEstado()); 
        assertEquals(LocalDate.now(), solicitud.getFecha());
    }

    @Test
    void debeResponderCorrectamente() {
        String respuestaValida = "Sus datos han sido rectificados.";
        
        assertDoesNotThrow(() -> {
            arcoService.responder(solicitudDePrueba, respuestaValida);
        });

        assertEquals("respondida", solicitudDePrueba.getEstado());
        assertEquals(respuestaValida, solicitudDePrueba.getRespuesta());
    }

    @Test
    void ejecutarNoDebeLanzarException() {
        assertDoesNotThrow(() -> {
            arcoService.ejecutar();
        });
    }

}
