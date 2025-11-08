package com.irentaspro.notif.domain.services;

import java.time.LocalDateTime;

import com.irentaspro.notif.domain.model.Notificacion;
import com.irentaspro.notif.domain.model.Plantilla;
import com.irentaspro.notif.domain.model.Scheduler;

public class NotIficacionService {
    private Scheduler scheduler = new Scheduler();

    public void NotificationService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Notificacion enviarCorreo(String destinatario, Plantilla plantilla, String... datos) {
        String contenido = plantilla.renderizar(datos);
        Notificacion notificacion = new Notificacion(
                destinatario,
                plantilla.getNombre(),
                contenido,
                "correo",
                "pendiente",
                plantilla);

        notificacion.enviar();
        return notificacion;
    }

    public void enviarSMS(String numero, String mensaje) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Número de destino inválido");
        }
        System.out.println("📱 SMS enviado a " + numero + ": " + mensaje);
    }

    public void programarEnvio(Notificacion notificacion, LocalDateTime fechaEjecucion) {
        scheduler.programar(fechaEjecucion, notificacion::enviar);
    }
}
