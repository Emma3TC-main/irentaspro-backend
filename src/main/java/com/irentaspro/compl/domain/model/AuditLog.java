package com.irentaspro.compl.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

/**
 * Representa un registro de auditoría dentro del sistema.
 * Permite rastrear acciones relevantes ejecutadas por usuarios.
 */
public class AuditLog extends Entidad {

    private UUID usuarioId;
    private String entidad;
    private String accion;
    private LocalDateTime fecha;
    private String ip;
    private HashEvidencia hashEvidencia;

    private AuditLog(UUID id, UUID usuarioId, String entidad, String accion, LocalDateTime fecha, String ip,
            HashEvidencia hashEvidencia) {
        super(id);
        this.usuarioId = usuarioId;
        this.entidad = entidad;
        this.accion = accion;
        this.fecha = fecha;
        this.ip = ip;
        this.hashEvidencia = hashEvidencia;
        validarInvariantes();
    }

    public static AuditLog crear(UUID usuarioId, String entidad, String accion, String ip,
            HashEvidencia hashEvidencia) {
        return new AuditLog(UUID.randomUUID(), usuarioId, entidad, accion, LocalDateTime.now(), ip, hashEvidencia);
    }

    @Override
    public void validarInvariantes() {
        if (usuarioId == null)
            throw new IllegalArgumentException("El usuarioId no puede ser nulo.");
        if (entidad == null || entidad.isBlank())
            throw new IllegalArgumentException("La entidad no puede estar vacía.");
        if (accion == null || accion.isBlank())
            throw new IllegalArgumentException("La acción no puede estar vacía.");
    }

    // Getters
    public UUID getUsuarioId() {
        return usuarioId;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getAccion() {
        return accion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getIp() {
        return ip;
    }

    public HashEvidencia getHashEvidencia() {
        return hashEvidencia;
    }
}
