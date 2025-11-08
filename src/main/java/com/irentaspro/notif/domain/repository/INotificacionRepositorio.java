package com.irentaspro.notif.domain.repository;

import java.util.List;

import com.irentaspro.common.domain.model.Repositorio;
import com.irentaspro.notif.domain.model.Notificacion;

public interface INotificacionRepositorio extends Repositorio<Notificacion> {
    List<Notificacion> buscarPorDestinatario(String destinatario);
    List<Notificacion> buscarPorEstado(String estado);
}
