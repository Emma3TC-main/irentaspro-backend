package com.irentaspro.compl.domain.model;

import java.time.LocalDate;

import com.irentaspro.common.domain.model.Entidad;

public class SolicitudARCO extends Entidad {
    public enum TipoSolicitud {
        ACCESO, RECTIFICACION, CANCELACION, OPOSICION
    }

    private TipoSolicitud tipoSolicitud;
    private LocalDate fecha;
    private String estado;
    private String respuesta;

    public SolicitudARCO(TipoSolicitud tipoSolicitud, LocalDate fecha, String estado, String respuesta) {
        this.tipoSolicitud = tipoSolicitud;
        this.fecha = fecha;
        this.estado = estado;
        this.respuesta = respuesta;
    }

    public void registrar() {
        this.fecha = LocalDate.now();
        this.estado = "registrada";
    }

    public void responder(String respuesta) {
        this.respuesta = respuesta;
        this.estado = "respondida";
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
