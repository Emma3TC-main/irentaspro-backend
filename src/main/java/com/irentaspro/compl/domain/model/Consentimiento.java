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
