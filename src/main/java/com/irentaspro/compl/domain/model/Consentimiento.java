package com.irentaspro.compl.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

/**
 * Representa el consentimiento otorgado por un usuario
 * sobre el tratamiento de sus datos personales,
 * incluyendo la versión del texto legal aceptado y su estado actual.
 */
public class Consentimiento extends Entidad {

    private final String texto;
    private final String version;
    private final UUID usuarioId;
    private LocalDate fechaAceptacion;
    private boolean aceptado;

    /**
     * Crea un consentimiento asociado a un usuario, inicialmente no aceptado.
     * 
     * @param texto     Texto del consentimiento.
     * @param version   Versión del documento.
     * @param usuarioId Identificador del usuario asociado.
     */
    public Consentimiento(String texto, String version, UUID usuarioId) {
        super();
        this.texto = texto;
        this.version = version;
        this.usuarioId = usuarioId;
        this.aceptado = false;
        this.fechaAceptacion = null;
        validarInvariantes();
    }

    /**
     * Marca el consentimiento como aceptado y registra la fecha actual.
     */
    public void aceptar() {
        this.aceptado = true;
        this.fechaAceptacion = LocalDate.now();
    }

    /**
     * Revoca el consentimiento sin eliminar el registro histórico.
     */
    public void revocar() {
        this.aceptado = false;
    }

    @Override
    public void validarInvariantes() {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto del consentimiento no puede estar vacío.");
        }
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("La versión del consentimiento no puede estar vacía.");
        }
        if (usuarioId == null) {
            throw new IllegalArgumentException("Debe asociarse un usuario válido al consentimiento.");
        }
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
