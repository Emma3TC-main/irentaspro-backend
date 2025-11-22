package com.irentaspro.compl.domain.model;

import java.time.LocalDate;
import java.util.UUID;
import com.irentaspro.common.domain.model.Entidad;

public class SolicitudARCO extends Entidad {

    public enum TipoSolicitud {
        ACCESO, RECTIFICACION, CANCELACION, OPOSICION
    }

    public enum EstadoSolicitud {
        REGISTRADA, RESPONDIDA
    }

    private final TipoSolicitud tipoSolicitud;
    private final LocalDate fecha;
    private EstadoSolicitud estado;
    private String respuesta;
    private final UUID usuarioId;

    public SolicitudARCO(UUID usuarioId, TipoSolicitud tipoSolicitud) {
        super();
        this.usuarioId = usuarioId;
        this.tipoSolicitud = tipoSolicitud;
        this.fecha = LocalDate.now();
        this.estado = EstadoSolicitud.REGISTRADA;
        this.respuesta = null;
        validarInvariantes();
    }

    private SolicitudARCO(UUID id, UUID usuarioId, TipoSolicitud tipo, LocalDate fecha,
            EstadoSolicitud estado, String respuesta) {
        super(id);
        this.usuarioId = usuarioId;
        this.tipoSolicitud = tipo;
        this.fecha = fecha;
        this.estado = estado;
        this.respuesta = respuesta;
        validarInvariantes();
    }

    public static SolicitudARCO reconstruir(UUID id, UUID usuarioId, TipoSolicitud tipo,
            LocalDate fecha, EstadoSolicitud estado, String respuesta) {
        return new SolicitudARCO(id, usuarioId, tipo, fecha, estado, respuesta);
    }

    public void responder(String respuesta) {
        if (estado != EstadoSolicitud.REGISTRADA)
            throw new IllegalStateException("La solicitud ya fue respondida.");
        if (respuesta == null || respuesta.isBlank())
            throw new IllegalArgumentException("La respuesta no puede estar vacía.");

        this.respuesta = respuesta;
        this.estado = EstadoSolicitud.RESPONDIDA;
        validarInvariantes();
    }

    @Override
    public void validarInvariantes() {
        if (tipoSolicitud == null)
            throw new IllegalArgumentException("El tipo de solicitud no puede ser nulo.");
        if (fecha == null)
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        if (fecha.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        if (estado == null)
            throw new IllegalArgumentException("El estado no puede ser nulo.");
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

}
