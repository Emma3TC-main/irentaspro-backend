package com.irentaspro.notif.application.usecases;

import com.irentaspro.notif.domain.service.CanalEnvio;
import com.irentaspro.notif.domain.model.Notificacion;
import com.irentaspro.notif.domain.repository.NotificacionRepository;

import java.util.List;

public class ProcesarNotificacionesPendientesUseCase {
    private final NotificacionRepository repo;
    private final CanalEnvio canal;

    public ProcesarNotificacionesPendientesUseCase(NotificacionRepository repo, CanalEnvio canal) {
        this.repo = repo;
        this.canal = canal;
    }

    public void ejecutar() {
        List<Notificacion> pendientes = repo.listarPendientes();
        pendientes.forEach(n -> {
            try {
                canal.enviar(n);
                n.marcarEnviada();
            } catch (Exception e) {
                n.marcarError();
            }
            repo.guardar(n);
        });
    }
}