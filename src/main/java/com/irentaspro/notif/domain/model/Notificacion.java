package com.irentaspro.notif.domain.model;

import com.irentaspro.common.domain.model.Entidad;
import java.util.UUID;

public class Notificacion extends Entidad {
    private final String destinatario;
    private final String asunto;
    private final String mensaje;
    private final String tipo; // EMAIL | SMS | PUSH
    private String estado; // PENDIENTE | ENVIADA | ERROR

    public Notificacion(UUID id, String destinatario, String asunto, String mensaje, String tipo) {
        super(id);
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.estado = "PENDIENTE";
        validarInvariantes();
    }

    public void marcarEnviada() {
        this.estado = "ENVIADA";
    }

    public void marcarError() {
        this.estado = "ERROR";
    }

    @Override
    public void validarInvariantes() {
        if (destinatario == null || destinatario.isBlank())
            throw new IllegalArgumentException("El destinatario no puede ser vacío");
        if (tipo == null)
            throw new IllegalArgumentException("El tipo no puede ser nulo");
    }

    public String destinatario() {
        return destinatario;
    }

    public String asunto() {
        return asunto;
    }

    public String mensaje() {
        return mensaje;
    }

    public String tipo() {
        return tipo;
    }

    public String estado() {
        return estado;
    }
}