package com.irentaspro.notif.domain.model;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class NotificacionTest {

private Plantilla plantillaValida = new Plantilla("PlantillaDePrueba", "<html>OK</html>");    

@Test
    void lanzarExceptionSiDestinatarioEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Notificacion(null, "Asunto", "Mensaje", "correo", "pendiente", plantillaValida);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Notificacion("  ", "Asunto", "Mensaje", "correo", "pendiente", plantillaValida);
        });
    }

    @Test
    void lanzarExceptionSiAsuntoEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Notificacion("test@test.com", null, "Mensaje", "correo", "pendiente", plantillaValida);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Notificacion("test@test.com", "", "Mensaje", "correo", "pendiente", plantillaValida);
        });
    }

    @Test
    void lanzarExceptionSiMensajeEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Notificacion("test@test.com", "Asunto", null, "correo", "pendiente", plantillaValida);
        });
    }
    
    @Test
    void lanzarExceptionSiPlantillaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Notificacion("test@test.com", "Asunto", "Mensaje", "correo", "pendiente", null);
        });
    }

    @Test
    void enviarDebeCambiarEstadoAEnviada() {
        Notificacion notificacion = new Notificacion(
            "test@valido.com", "Asunto", "Mensaje", "correo", "pendiente", plantillaValida
        );

        assertEquals("pendiente", notificacion.getEstado()); // Verificamos estado inicial

        notificacion.enviar();
        assertEquals("enviada", notificacion.getEstado());
    }

    @Test
    void debeCrearNotificacionValida() {
        String dest = "destinatario@valido.com";
        String asunto = "Asunto Válido";
        String msg = "Mensaje Válido";

        Notificacion notificacion = assertDoesNotThrow(() -> {
            return new Notificacion(dest, asunto, msg, "correo", "pendiente", plantillaValida);
        });

        assertNotNull(notificacion);
        assertNotNull(notificacion.getId()); 
        assertEquals(dest, notificacion.getDestinatario());
        assertEquals(asunto, notificacion.getAsunto());
        assertEquals(msg, notificacion.getMensaje());
        assertEquals(plantillaValida, notificacion.getPlantilla());
    }

}
