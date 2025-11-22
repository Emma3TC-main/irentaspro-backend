package com.irentaspro.compl.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.irentaspro.compl.application.service.ComplianceApplicationService;
import com.irentaspro.compl.application.dto.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceApplicationService service;

    // ------------------------------------------------------------
    // CONSENTIMIENTOS
    // ------------------------------------------------------------

    /** Registrar y aceptar consentimiento */
    @PostMapping("/consentimientos")
    public ResponseEntity<ConsentimientoDTO> aceptarConsentimiento(
            @RequestBody ConsentimientoCreateRequest req,
            @RequestHeader(value = "X-Forwarded-For", required = false) String ip) {

        var dto = service.registrarConsentimiento(
                req.usuarioId(),
                req.texto(),
                req.version(),
                (ip == null ? "unknown" : ip));

        return ResponseEntity.ok(dto);
    }

    /** Obtener consentimiento del usuario */
    @GetMapping("/consentimientos/usuario/{usuarioId}")
    public ResponseEntity<ConsentimientoDTO> obtenerConsentimientoActual(
            @PathVariable UUID usuarioId) {

        var dto = service.obtenerConsentimientoPorUsuario(usuarioId);
        return ResponseEntity.ok(dto);
    }

    // ------------------------------------------------------------
    // SOLICITUDES ARCO
    // ------------------------------------------------------------

    /** Crear una solicitud ARCO */
    @PostMapping("/arco")
    public ResponseEntity<SolicitudARCODTO> crearARCO(
            @RequestBody SolicitudARCOCreateRequest req) {

        var dto = service.registrarSolicitudARCO(
                req.usuarioId(),
                com.irentaspro.compl.domain.model.SolicitudARCO.TipoSolicitud.valueOf(req.tipo()));

        return ResponseEntity.ok(dto);
    }

    /** Obtener una solicitud por ID */
    @GetMapping("/arco/{id}")
    public ResponseEntity<SolicitudARCODTO> obtenerARCO(@PathVariable UUID id) {
        var dto = service.obtenerSolicitudARCO(id);
        return ResponseEntity.ok(dto);
    }

    /** Listar solicitudes ARCO de un usuario */
    @GetMapping("/arco/usuario/{usuarioId}")
    public ResponseEntity<List<SolicitudARCODTO>> listarPorUsuario(
            @PathVariable UUID usuarioId) {

        var lista = service.listarSolicitudesPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    /** Listar todas las solicitudes ARCO (solo admin) */
    @GetMapping("/arco")
    public ResponseEntity<List<SolicitudARCODTO>> listarTodas() {
        var lista = service.listarTodasSolicitudes();
        return ResponseEntity.ok(lista);
    }

    /** Responder solicitud ARCO */
    @PostMapping("/arco/{id}/responder")
    public ResponseEntity<Void> responder(
            @PathVariable UUID id,
            @RequestBody ResponderARCORequest r) {

        service.responderSolicitudARCO(id, r.respuesta(), r.adminId());
        return ResponseEntity.noContent().build();
    }
}
