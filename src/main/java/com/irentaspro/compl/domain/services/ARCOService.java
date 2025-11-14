package com.irentaspro.compl.domain.services;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.compl.domain.model.SolicitudARCO;

public class ARCOService implements ServiciosDominio {
    @Override
    public void ejecutar() {
        // Implementación general opcional
    }

    public SolicitudARCO registrarSolicitud(SolicitudARCO.TipoSolicitud tipo) {
        SolicitudARCO solicitud = new SolicitudARCO(tipo, java.time.LocalDate.now(), "registrada", null);
        System.out.println("Solicitud ARCO registrada: " + tipo);
        return solicitud;
    }

    public void responder(SolicitudARCO solicitud, String respuesta) {
        //TDD
        // ¡Esta es la lógica que tu test ROJO exige!
        if (solicitud == null) {
            throw new IllegalArgumentException("La 'solicitud' a responder no puede ser nula.");
        }
        
        solicitud.responder(respuesta);
        System.out.println("Solicitud ARCO respondida: " + respuesta);
    }
}
