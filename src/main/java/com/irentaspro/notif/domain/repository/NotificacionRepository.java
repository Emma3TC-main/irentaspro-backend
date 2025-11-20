package com.irentaspro.notif.domain.repository;

import com.irentaspro.notif.domain.model.Notificacion;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface NotificacionRepository {
    Notificacion guardar(Notificacion n);

    Optional<Notificacion> buscarPorId(UUID id);

    List<Notificacion> listarPendientes();
}