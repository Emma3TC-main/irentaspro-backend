package com.irentaspro.notif.infrastructure.config;

import com.irentaspro.notif.domain.repository.NotificacionRepository;
import com.irentaspro.notif.domain.service.CanalEnvio;
import com.irentaspro.notif.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifConfig {

    @Bean
    public CrearNotificacionUseCase crearNotificacionUseCase(NotificacionRepository repo) {
        return new CrearNotificacionUseCase(repo);
    }

    @Bean
    public ProcesarNotificacionesPendientesUseCase procesarNotificacionesPendientesUseCase(
            NotificacionRepository repo,
            CanalEnvio canal) {
        return new ProcesarNotificacionesPendientesUseCase(repo, canal);
    }
}