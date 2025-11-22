package com.irentaspro.compl.infrastructure.persistence.mapper;

import java.util.Objects;

import com.irentaspro.compl.domain.model.AuditLog;
import com.irentaspro.compl.domain.model.Consentimiento;
import com.irentaspro.compl.domain.model.HashEvidencia;
import com.irentaspro.compl.domain.model.SolicitudARCO;
import com.irentaspro.compl.infrastructure.persistence.entity.AuditLogEntity;
import com.irentaspro.compl.infrastructure.persistence.entity.ConsentimientoEntity;
import com.irentaspro.compl.infrastructure.persistence.entity.SolicitudARCOEntity;
import com.irentaspro.iam.infrastructure.entity.UsuarioEntity;

public class ComplMapper {

    // ---------------- AuditLog ----------------
    public static AuditLogEntity toEntity(AuditLog d) {

        AuditLogEntity e = new AuditLogEntity();

        e.setId(d.getId());
        e.setUsuarioId(d.getUsuarioId());
        e.setEntidad(d.getEntidad());
        e.setAccion(d.getAccion());
        e.setFecha(d.getFecha());
        e.setIp(d.getIp());
        e.setHashEvidencia(
                d.getHashEvidencia() != null ? d.getHashEvidencia().toString() : null);

        return e;
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
    public static ConsentimientoEntity toEntity(Consentimiento c, UsuarioEntity usuario) {
        if (c == null)
            return null;

        ConsentimientoEntity e = new ConsentimientoEntity();
        e.setId(c.getId());
        e.setTexto(c.getTexto());
        e.setVersion(c.getVersion());
        e.setFechaAceptacion(c.getFechaAceptacion());
        e.setAceptado(c.isAceptado());
        e.setUsuario(usuario); // <--- FIX IMPORTANTE

        return e;
    }

    public static Consentimiento toDomain(ConsentimientoEntity e) {
        if (e == null)
            return null;
        return Consentimiento.reconstruir(
                e.getId(),
                e.getTexto(),
                e.getVersion(),
                e.getUsuarioId(), // <--- FIX USANDO EL GETTER
                e.isAceptado(),
                e.getFechaAceptacion());

    }

    // ---------------- SolicitudARCO ----------------
    public static SolicitudARCOEntity toEntity(SolicitudARCO s, UsuarioEntity usuario) {
        if (s == null)
            return null;

        SolicitudARCOEntity e = new SolicitudARCOEntity();
        e.setId(s.getId());
        e.setTipoSolicitud(s.getTipoSolicitud());
        e.setEstado(s.getEstado());
        e.setFecha(s.getFecha());
        e.setRespuesta(s.getRespuesta());
        e.setUsuario(usuario); // <--- FIX

        return e;
    }

    public static SolicitudARCO toDomain(SolicitudARCOEntity e) {
        if (e == null)
            return null;

        return SolicitudARCO.reconstruir(
                e.getId(),
                e.getUsuarioId(), // <--- FIX
                e.getTipoSolicitud(),
                e.getFecha(),
                e.getEstado(),
                e.getRespuesta());

    }

}
