package com.irentaspro.compl.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

public class Consentimiento extends Entidad {

    private String texto;
    private String version;
    private LocalDate fechaAceptacion;
    private UUID usuarioId;
    private boolean aceptado;

    public Consentimiento(String texto, String version, LocalDate fechaAceptacion, UUID usuarioId, boolean aceptado) {

        //TDD
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El 'texto' del consentimiento no puede ser nulo o vacío.");
        }
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("La 'version' del consentimiento no puede ser nula o vacía.");
        }
        if (fechaAceptacion == null) {
            throw new IllegalArgumentException("La 'fechaAceptacion' no puede ser nula.");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("El 'usuarioId' no puede ser nulo.");
        }
        
        this.texto = texto;
        this.version = version;
        this.fechaAceptacion = fechaAceptacion;
        this.usuarioId = usuarioId;
        this.aceptado = aceptado;
    }

    public void aceptar() {
        this.aceptado = true;
        this.fechaAceptacion = LocalDate.now();
    }

    public void revocar() {
        this.aceptado = false;
    }

    // Getters
    public String getTexto() {
        return texto;
    }

    public String getVersion() {
        return version;
    }

    public LocalDate getFechaAceptacion() {
        return fechaAceptacion;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public boolean isAceptado() {
        return aceptado;
    }
}
