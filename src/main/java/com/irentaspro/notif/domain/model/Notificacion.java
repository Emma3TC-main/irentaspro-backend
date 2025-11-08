package com.irentaspro.notif.domain.model;

import com.irentaspro.common.domain.model.Entidad;

public class Notificacion extends Entidad {
    private String destinatario;
    private String asunto;
    private String mensaje;
    private String tipo;
    private String estado;
    private Plantilla plantilla;

    public Notificacion(String destinatario, String asunto, String mensaje, String tipo, String estado,
            Plantilla plantilla) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.estado = estado;
        this.plantilla = plantilla;
    }

    public void enviar() {
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("El destinatario no puede estar vacío");
        }
        this.estado = "enviada";
        System.out.println("Notificación enviada a " + destinatario + " con asunto: " + asunto);
    }

    // Getters
    public String getDestinatario() {
        return destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public Plantilla getPlantilla() {
        return plantilla;
    }

    // Setters controlados
    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void asignarPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }
}
