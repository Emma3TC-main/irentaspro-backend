package com.irentaspro.notif.domain.service;

import com.irentaspro.notif.domain.model.Notificacion;

public interface CanalEnvio {
    void enviar(Notificacion notif);
}