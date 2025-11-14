package com.irentaspro.notif.domain.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.irentaspro.notif.domain.model.Notificacion;
import com.irentaspro.notif.domain.model.Plantilla;
import com.irentaspro.notif.domain.model.Scheduler;

class NotificacionServiceTest {
    private Plantilla plantillaValida;
    private Notificacion notificacionValida;

    @Mock
    private Scheduler mockScheduler;

    @InjectMocks
    private NotIficacionService notificacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        notificacionService.NotificationService(mockScheduler);
        
        // 5. Creamos objetos REALES para usar en los tests
        plantillaValida = new Plantilla("Test", "<html></html>");
        notificacionValida = new Notificacion("test@test.com", "Asunto", "Msg", "email", "pendiente", plantillaValida);
    }

    @Test
    void lanzarExceptionSiNotificacionEsNula() {
        LocalDateTime fechaValida = LocalDateTime.now().plusDays(1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.programarEnvio(null, fechaValida);
        });
    }

    @Test
    void lanzarExceptionSiFechaEsNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.programarEnvio(notificacionValida, null);
        });
    }


    @Test
    void debeLlamarAlSchedulerCorrectamente() {
        LocalDateTime fechaValida = LocalDateTime.now().plusDays(1);
        assertDoesNotThrow(() -> {
            notificacionService.programarEnvio(notificacionValida, fechaValida);
        });

        verify(mockScheduler, times(1)).programar(
            eq(fechaValida),        // (eq() es de Mockito, para comparar argumentos)
            any(Runnable.class)     // (No nos importa *qué* runnable, solo que sea uno)
        );
    }

    @Test
    void enviarSMSLanzaExceptionSiNumeroEsNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.enviarSMS(null, "Mensaje");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.enviarSMS("  ", "Mensaje");
        });
    }
    
    @Test
    void debeCrearYEnviarNotificacionCorreo() {
        String dest = "test@correo.com";
        String[] datos = {"Aaron"};
        
        Notificacion notificacionGenerada = assertDoesNotThrow(() -> {
            return notificacionService.enviarCorreo(dest, plantillaValida, datos);
        });

        assertNotNull(notificacionGenerada);
        assertEquals(dest, notificacionGenerada.getDestinatario());
        assertEquals("Test", notificacionGenerada.getAsunto()); 
        assertEquals("enviada", notificacionGenerada.getEstado()); 
    }

}
