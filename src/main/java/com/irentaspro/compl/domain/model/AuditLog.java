package com.irentaspro.compl.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

public class AuditLog extends Entidad {
    private UUID usuarioId;
    private String entidad;
    private String accion;
    private LocalDateTime fecha;
    private String ip;
    private HashEvidencia hashEvidencia;

    public AuditLog(UUID id, UUID usuarioId, String entidad, String accion, LocalDateTime fecha, String ip,
            HashEvidencia hashEvidencia) {
        this.usuarioId = usuarioId;
        this.entidad = entidad;
        this.accion = accion;
        this.fecha = fecha;
        this.ip = ip;
        this.hashEvidencia = hashEvidencia;
    }

    public static AuditLog crear(UUID usuarioId, String entidad, String accion, String ip,
            HashEvidencia hashEvidencia) {
        return new AuditLog(UUID.randomUUID(), usuarioId, entidad, accion, LocalDateTime.now(), ip, hashEvidencia);
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
