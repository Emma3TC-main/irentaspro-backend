package com.irentaspro.compl.infrastructure.persistence.mapper;

import java.util.Objects;

import com.irentaspro.compl.domain.model.AuditLog;
import com.irentaspro.compl.domain.model.Consentimiento;
import com.irentaspro.compl.domain.model.HashEvidencia;
import com.irentaspro.compl.domain.model.SolicitudARCO;
import com.irentaspro.compl.infrastructure.persistence.entity.AuditLogEntity;
import com.irentaspro.compl.infrastructure.persistence.entity.ConsentimientoEntity;
import com.irentaspro.compl.infrastructure.persistence.entity.SolicitudARCOEntity;

public class ComplMapper {

    // ---------------- AuditLog ----------------
    public static AuditLogEntity toEntity(AuditLog d) {
        String he = (d.getHashEvidencia() != null) ? d.getHashEvidencia().toString() : null;
        return new AuditLogEntity(d.getId(), d.getUsuarioId(), d.getEntidad(), d.getAccion(), d.getFecha(),
                d.getIp(), he);
    }

    public static AuditLog toDomain(AuditLogEntity e) {
        HashEvidencia h = (e.getHashEvidencia() != null) ? parseHash(e.getHashEvidencia()) : null;
        // Usar reconstruir para preservar id/fecha original
        return AuditLog.reconstruir(e.getId(), e.getUsuarioId(), e.getEntidad(), e.getAccion(), e.getFecha(),
                e.getIp(), h);
    }

    private static HashEvidencia parseHash(String s) {
        if (s == null)
            return null;
        String[] parts = s.split(":", 2);
        if (parts.length == 2)
            return new HashEvidencia(parts[1], parts[0]);
        return new HashEvidencia(s, "UNKNOWN");
    }

    // ---------------- Consentimiento ----------------
    public static ConsentimientoEntity toEntity(Consentimiento c) {
        if (c == null)
            return null;
        return new ConsentimientoEntity(
                c.getId(),
                c.getTexto(),
                c.getVersion(),
                c.getUsuarioId(),
                c.getFechaAceptacion(),
                c.isAceptado());
    }

    public static Consentimiento toDomain(ConsentimientoEntity e) {
        if (e == null)
            return null;
        return Consentimiento.reconstruir(e.getId(), e.getTexto(), e.getVersion(), e.getUsuarioId(), e.isAceptado(),
                e.getFechaAceptacion());
    }

    // ---------------- SolicitudARCO ----------------
    public static SolicitudARCOEntity toEntity(SolicitudARCO s) {
        if (s == null)
            return null;
        return new SolicitudARCOEntity(
                s.getId(),
                s.getUsuarioId(), 
                s.getTipoSolicitud(),
                s.getFecha(),
                s.getEstado(),
                s.getRespuesta());
    }

    public static SolicitudARCO toDomain(SolicitudARCOEntity e) {
        if (e == null)
            return null;

        return SolicitudARCO.reconstruir(
                e.getId(),
                e.getUsuarioId(),
                e.getTipoSolicitud(),
                e.getFecha(),
                e.getEstado(),
                e.getRespuesta());
    }

}
