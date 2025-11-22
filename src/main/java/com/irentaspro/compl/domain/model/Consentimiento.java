package com.irentaspro.compl.domain.model;

import java.time.LocalDate;
import java.util.UUID;
import com.irentaspro.common.domain.model.Entidad;

public class Consentimiento extends Entidad {

    private final String texto;
    private final String version;
    private final UUID usuarioId;
    private boolean aceptado;
    private LocalDate fechaAceptacion;

    private Consentimiento(UUID id,
            String texto,
            String version,
            UUID usuarioId,
            boolean aceptado,
            LocalDate fechaAceptacion) {
        super(id);
        this.texto = texto;
        this.version = version;
        this.usuarioId = usuarioId;
        this.aceptado = aceptado;
        this.fechaAceptacion = fechaAceptacion;
        validarInvariantes();
    }

    public static Consentimiento crearNuevo(String texto, String version, UUID usuarioId) {
        return new Consentimiento(
                UUID.randomUUID(),
                texto,
                version,
                usuarioId,
                false,
                null);
    }

    public static Consentimiento reconstruir(UUID id, String texto, String version,
            UUID usuarioId, boolean aceptado,
            LocalDate fechaAceptacion) {
        return new Consentimiento(id, texto, version, usuarioId, aceptado, fechaAceptacion);
    }

    public void aceptar() {
        if (aceptado)
            throw new IllegalStateException("El consentimiento ya está aceptado.");
        aceptado = true;
        fechaAceptacion = LocalDate.now();
        validarInvariantes();
    }

    public void revocar() {
        if (!aceptado)
            throw new IllegalStateException("El consentimiento ya está revocado.");
        aceptado = false;
        validarInvariantes();
    }

    @Override
    public void validarInvariantes() {
        if (texto == null || texto.isBlank())
            throw new IllegalArgumentException("El texto del consentimiento no puede estar vacío.");
        if (version == null || version.isBlank())
            throw new IllegalArgumentException("La versión del consentimiento no puede estar vacía.");
        if (usuarioId == null)
            throw new IllegalArgumentException("Debe existir un usuario asociado.");

        if (fechaAceptacion != null && fechaAceptacion.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de aceptación no puede ser futura.");
    }

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
