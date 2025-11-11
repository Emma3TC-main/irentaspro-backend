package com.irentaspro.compl.domain.model;

import java.time.LocalDate;

import com.irentaspro.common.domain.model.Entidad;

/**
 * Representa una solicitud ARCO (Acceso, Rectificación, Cancelación u
 * Oposición)
 * realizada por un titular de datos personales.
 */
public class SolicitudARCO extends Entidad {

    public enum TipoSolicitud {
        ACCESO, RECTIFICACION, CANCELACION, OPOSICION
    }

    private TipoSolicitud tipoSolicitud;
    private LocalDate fecha;
    private String estado;
    private String respuesta;

    public SolicitudARCO(TipoSolicitud tipoSolicitud) {
        super();
        this.tipoSolicitud = tipoSolicitud;
        this.fecha = LocalDate.now();
        this.estado = "registrada";
        this.respuesta = null;
        validarInvariantes();
    }

    public void responder(String respuesta) {
        this.respuesta = respuesta;
        this.estado = "respondida";
    }

    @Override
    public void validarInvariantes() {
        if (tipoSolicitud == null)
            throw new IllegalArgumentException("El tipo de solicitud no puede ser nulo.");
    }

    // Getters
    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public String getRespuesta() {
        return respuesta;
    }
}
