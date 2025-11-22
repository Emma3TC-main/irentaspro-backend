package com.irentaspro.compl.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

public class AuditLog extends Entidad {

    private final UUID usuarioId;
    private final String entidad;
    private final String accion;
    private final LocalDateTime fecha;
    private final String ip;
    private final HashEvidencia hashEvidencia;

    private AuditLog(UUID id, UUID usuarioId, String entidad, String accion,
            LocalDateTime fecha, String ip, HashEvidencia hashEvidencia) {
        super(id);
        this.usuarioId = usuarioId;
        this.entidad = entidad;
        this.accion = accion;
        this.fecha = fecha;
        this.ip = ip;
        this.hashEvidencia = hashEvidencia;
        validarInvariantes();
    }

    public static AuditLog crear(UUID usuarioId, String entidad, String accion,
            String ip, HashEvidencia hashEvidencia) {
        return new AuditLog(
                UUID.randomUUID(),
                usuarioId,
                entidad,
                accion,
                LocalDateTime.now(),
                ip,
                hashEvidencia);
    }

    public static AuditLog reconstruir(UUID id, UUID usuarioId, String entidad, String accion,
            LocalDateTime fecha, String ip, HashEvidencia hashEvidencia) {
        return new AuditLog(id, usuarioId, entidad, accion, fecha, ip, hashEvidencia);
    }

    @Override
    public void validarInvariantes() {
        if (usuarioId == null)
            throw new IllegalArgumentException("El usuarioId no puede ser nulo.");
        if (entidad == null || entidad.isBlank())
            throw new IllegalArgumentException("La entidad no puede estar vacía.");
        if (accion == null || accion.isBlank())
            throw new IllegalArgumentException("La acción no puede estar vacía.");
        if (fecha == null)
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        if (fecha.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        if (ip == null || ip.isBlank())
            throw new IllegalArgumentException("La IP no puede estar vacía.");
        if (hashEvidencia == null)
            throw new IllegalArgumentException("La evidencia no puede ser nula.");
    }

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
