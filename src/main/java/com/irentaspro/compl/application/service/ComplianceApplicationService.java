package com.irentaspro.compl.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.irentaspro.compl.domain.repository.*;
import com.irentaspro.compl.application.dto.*;
import com.irentaspro.compl.domain.model.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplianceApplicationService {

    private final ConsentimientoRepository consentimientoRepo;
    private final AuditLogRepository auditRepo;
    private final SolicitudARCORepository arcoRepo;

    // ============================================================
    // 1. REGISTRAR CONSENTIMIENTO
    // ============================================================
    @Transactional
    public ConsentimientoDTO registrarConsentimiento(UUID usuarioId, String texto, String version, String ip) {

        // Crear desde Dominio
        Consentimiento c = Consentimiento.crearNuevo(texto, version, usuarioId);
        c.aceptar();

        consentimientoRepo.guardar(c);

        // Auditoría
        AuditLog log = AuditLog.crear(
                usuarioId,
                "Consentimiento",
                "ACEPTAR",
                ip,
                new HashEvidencia("hash-" + c.getId(), "SHA-256"));

        auditRepo.guardar(log);

        return new ConsentimientoDTO(
                c.getId(),
                c.getUsuarioId(),
                c.getTexto(),
                c.getVersion(),
                c.getFechaAceptacion(),
                c.isAceptado());
    }

    // ============================================================
    // 2. OBTENER CONSENTIMIENTO ACTUAL DEL USUARIO
    // ============================================================
    @Transactional(readOnly = true)
    public ConsentimientoDTO obtenerConsentimientoPorUsuario(UUID usuarioId) {

        var opt = consentimientoRepo.buscarPorUsuario(usuarioId);

        if (opt.isEmpty())
            return null;

        Consentimiento c = opt.get();

        return new ConsentimientoDTO(
                c.getId(),
                c.getUsuarioId(),
                c.getTexto(),
                c.getVersion(),
                c.getFechaAceptacion(),
                c.isAceptado());
    }

    // ============================================================
    // 3. CREAR SOLICITUD ARCO
    // ============================================================
    @Transactional
    public SolicitudARCODTO registrarSolicitudARCO(UUID usuarioId, SolicitudARCO.TipoSolicitud tipo) {

        SolicitudARCO s = new SolicitudARCO(usuarioId, tipo);
        arcoRepo.guardar(s);

        auditRepo.guardar(
                AuditLog.crear(
                        usuarioId,
                        "SolicitudARCO",
                        "CREAR",
                        "0.0.0.0",
                        new HashEvidencia("none", "NONE")));

        return toDTO(s);
    }

    // ============================================================
    // 4. OBTENER SOLICITUD ARCO POR ID
    // ============================================================
    @Transactional(readOnly = true)
    public SolicitudARCODTO obtenerSolicitudARCO(UUID solicitudId) {
        var opt = arcoRepo.buscarPorId(solicitudId);

        if (opt.isEmpty())
            throw new IllegalArgumentException("Solicitud ARCO no encontrada");

        return toDTO(opt.get());
    }

    // ============================================================
    // 5. LISTAR SOLICITUDES ARCO POR USUARIO
    // ============================================================
    @Transactional(readOnly = true)
    public List<SolicitudARCODTO> listarSolicitudesPorUsuario(UUID usuarioId) {
        return arcoRepo.buscarPorUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ============================================================
    // 6. LISTAR TODAS LAS SOLICITUDES (ADMIN)
    // ============================================================
    @Transactional(readOnly = true)
    public List<SolicitudARCODTO> listarTodasSolicitudes() {
        return arcoRepo.buscarTodos()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ============================================================
    // 7. RESPONDER SOLICITUD ARCO
    // ============================================================
    @Transactional
    public void responderSolicitudARCO(UUID solicitudId, String respuesta, UUID adminId) {

        var opt = arcoRepo.buscarPorId(solicitudId);

        if (opt.isEmpty())
            throw new IllegalArgumentException("Solicitud ARCO no encontrada");

        SolicitudARCO s = opt.get();
        s.responder(respuesta);

        arcoRepo.guardar(s);

        auditRepo.guardar(
                AuditLog.crear(
                        adminId,
                        "SolicitudARCO",
                        "RESPONDER",
                        "0.0.0.0",
                        new HashEvidencia("none", "NONE")));
    }

    // ============================================================
    // MAPPER LOCAL
    // ============================================================
    private SolicitudARCODTO toDTO(SolicitudARCO s) {
        return new SolicitudARCODTO(
                s.getId(),
                s.getUsuarioId(),
                s.getTipoSolicitud().name(),
                s.getFecha(),
                s.getEstado(),
                s.getRespuesta());
    }
}
