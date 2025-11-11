package com.irentaspro.compl.domain.services;

import java.time.LocalDate;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.compl.domain.model.SolicitudARCO;

/**
 * Servicio de dominio responsable de gestionar las operaciones relacionadas
 * con las solicitudes ARCO (Acceso, Rectificación, Cancelación u Oposición).
 * 
 * Este servicio actúa sobre la entidad {@link SolicitudARCO}, orquestando su
 * creación
 * y actualización, manteniendo las reglas del dominio.
 */
public class ARCOService implements ServiciosDominio {

    @Override
    public void ejecutar() {
        // Implementación general opcional o punto de entrada del servicio
        System.out.println("Ejecución del servicio ARCO iniciada.");
    }

    /**
     * Registra una nueva solicitud ARCO de un tipo determinado.
     * 
     * @param tipo Tipo de solicitud (ACCESO, RECTIFICACION, etc.)
     * @return Una nueva instancia de SolicitudARCO registrada.
     */
    public SolicitudARCO registrarSolicitud(SolicitudARCO.TipoSolicitud tipo) {
        SolicitudARCO solicitud = new SolicitudARCO(tipo);
        System.out.println("[ARCO] Solicitud registrada: " + tipo);
        return solicitud;
    }

    /**
     * Responde una solicitud ARCO existente con una respuesta.
     * 
     * @param solicitud Solicitud ARCO existente.
     * @param respuesta Texto de la respuesta.
     */
    public void responder(SolicitudARCO solicitud, String respuesta) {
        solicitud.responder(respuesta);
        System.out.println("[ARCO] Solicitud respondida: " + respuesta);
    }
}
