package com.irentaspro.notif.domain.model;

import java.util.UUID;
import com.irentaspro.common.domain.model.Entidad;

/**
 * Entidad de dominio: Notificación
 * Representa una notificación enviada al usuario (correo, SMS, push, etc.).
 * Incluye información del destinatario, contenido, tipo y estado de envío.
 */
public class Notificacion extends Entidad {

    private String destinatario;
    private String asunto;
    private String mensaje;
    private String tipo; // Ejemplo: "EMAIL", "SMS", "PUSH"
    private String estado; // Ejemplo: "pendiente", "enviada", "fallida"
    private Plantilla plantilla;

    /**
     * Constructor principal (para creación de una nueva notificación).
     */
    public Notificacion(String destinatario, String asunto, String mensaje, String tipo, String estado,
            Plantilla plantilla) {
        super(); // genera UUID
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.estado = estado != null ? estado : "pendiente";
        this.plantilla = plantilla;
        validarInvariantes();
    }

    /**
     * Constructor usado para reconstrucción desde persistencia.
     */
    public Notificacion(UUID id, String destinatario, String asunto, String mensaje, String tipo, String estado,
            Plantilla plantilla) {
        super(id);
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.estado = estado;
        this.plantilla = plantilla;
        validarInvariantes();
    }

    // --- Lógica de negocio ---
    public void enviar() {
        if (estado.equalsIgnoreCase("enviada")) {
            throw new IllegalStateException("La notificación ya fue enviada.");
        }
        if (destinatario == null || destinatario.isBlank()) {
            throw new IllegalArgumentException("El destinatario no puede estar vacío.");
        }
        this.estado = "enviada";
        // Aquí podría dispararse un evento de dominio: NotificacionEnviada
    }

    public void marcarFallida(String motivo) {
        this.estado = "fallida";
        // Podría registrarse un evento o log de error con el motivo
    }

    // --- Getters ---
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

    // --- Setters controlados ---
    public void actualizarEstado(String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        this.estado = nuevoEstado;
    }

    public void asignarPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }

    // --- Validación de invariantes ---
    @Override
    public void validarInvariantes() {
        if (asunto == null || asunto.isBlank()) {
            throw new IllegalArgumentException("El asunto no puede estar vacío.");
        }
        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío.");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificación es obligatorio.");
        }
    }
}
