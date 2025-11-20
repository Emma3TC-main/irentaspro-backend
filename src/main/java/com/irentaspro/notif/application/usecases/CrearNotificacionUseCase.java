package com.irentaspro.notif.application.usecases;

import com.irentaspro.notif.domain.model.Notificacion;
import com.irentaspro.notif.domain.repository.NotificacionRepository;

import java.util.UUID;

public class CrearNotificacionUseCase {
    private final NotificacionRepository repo;

    public CrearNotificacionUseCase(NotificacionRepository repo) {
        this.repo = repo;
    }

    public UUID ejecutar(String destinatario, String asunto, String mensaje, String tipo) {
        Notificacion notif = new Notificacion(UUID.randomUUID(), destinatario, asunto, mensaje, tipo);
        repo.guardar(notif);
        return notif.getId();
    }
}