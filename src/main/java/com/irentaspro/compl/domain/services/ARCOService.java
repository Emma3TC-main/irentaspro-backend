package com.irentaspro.compl.domain.services;

import java.util.UUID;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.compl.domain.model.SolicitudARCO;

public class ARCOService implements ServiciosDominio {

    @Override
    public void ejecutar() {
        // Punto de entrada opcional del dominio — sin prints
    }

    /**
     * Registra una nueva solicitud ARCO de un tipo determinado.
     */
    public SolicitudARCO registrarSolicitud(UUID usuarioId, SolicitudARCO.TipoSolicitud tipo) {
        return new SolicitudARCO(usuarioId, tipo);
    }

    /**
     * Responde una solicitud ARCO existente con una respuesta.
     */
    public void responder(SolicitudARCO solicitud, String respuesta) {
        solicitud.responder(respuesta);
    }
}
