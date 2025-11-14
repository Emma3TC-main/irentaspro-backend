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
        
        if (tipoSolicitud == null) {
            throw new IllegalArgumentException("El 'tipoSolicitud' no puede ser nulo.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La 'fecha' no puede ser nula.");
        }
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("El 'estado' no puede ser nulo o vacío.");
        }
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
        if (respuesta == null || respuesta.isBlank()) {
            throw new IllegalArgumentException("La 'respuesta' no puede ser nula o vacía.");
        }
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
