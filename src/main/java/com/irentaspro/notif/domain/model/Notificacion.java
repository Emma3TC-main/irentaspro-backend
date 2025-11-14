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
                
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("El destinatario no puede ser nulo o vacío.");
        }
        if (asunto == null || asunto.isEmpty()) {
            throw new IllegalArgumentException("El asunto no puede ser nulo o vacío.");
        }
        if (mensaje == null) { // El mensaje podría estar vacío, pero no nulo
            throw new IllegalArgumentException("El mensaje no puede ser nulo.");
        }
        if (plantilla == null) {
            throw new IllegalArgumentException("La plantilla no puede ser nula.");
        }

        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.estado = estado;
        this.plantilla = plantilla;
    }

    public void enviar() {
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
