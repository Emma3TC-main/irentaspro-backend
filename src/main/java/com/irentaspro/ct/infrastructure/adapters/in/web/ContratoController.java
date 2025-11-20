package com.irentaspro.ct.infrastructure.adapters.in.web;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irentaspro.ct.application.dto.CalendarioCuotasDTO;
import com.irentaspro.ct.application.dto.ContratoDTO;
import com.irentaspro.ct.application.dto.ContratoResponseDTO;
import com.irentaspro.ct.application.dto.CrearContratoDTO;
import com.irentaspro.ct.application.dto.FirmarContratoDTO;
import com.irentaspro.ct.application.dto.PagoRealizadoDTO;
import com.irentaspro.ct.application.dto.FinalizarContratoDTO;
import com.irentaspro.ct.application.dto.RegistrarPagoContratoDTO;
import com.irentaspro.ct.application.dto.RenovarContratoDTO;
import com.irentaspro.ct.application.service.ContratoAppService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoAppService contratoAppService;

    public ContratoController(ContratoAppService contratoAppService) {
        this.contratoAppService = contratoAppService;
    }

    // Crear contrato
    @PostMapping
    public ResponseEntity<ContratoResponseDTO> crearContrato(
            @Valid @RequestBody CrearContratoDTO dto) {

        ContratoResponseDTO creado = contratoAppService.crearContrato(dto);
        URI location = URI.create("/contratos/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    // Firmar contrato
    @PostMapping("/{id}/firmar")
    public ResponseEntity<ContratoResponseDTO> firmarContrato(
            @PathVariable UUID id,
            @Valid @RequestBody(required = false) FirmarContratoDTO dto) {

        return ResponseEntity.ok(contratoAppService.firmarContrato(id, dto));
    }

    // Generar calendario
    @PostMapping("/{id}/calendario")
    public ResponseEntity<CalendarioCuotasDTO> generarCalendario(@PathVariable UUID id) {
        return ResponseEntity.ok(contratoAppService.generarCalendario(id));
    }

    // Registrar pago : Pago local del inquilino
    @PostMapping("/{id}/pago")
    public ResponseEntity<ContratoResponseDTO> registrarPago(
            @PathVariable UUID id,
            @Valid @RequestBody RegistrarPagoContratoDTO dto) {

        return ResponseEntity.ok(contratoAppService.registrarPago(id, dto));
    }

    // Renovar contrato
    @PostMapping("/{id}/renovar")
    public ResponseEntity<ContratoResponseDTO> renovarContrato(
            @PathVariable UUID id,
            @Valid @RequestBody RenovarContratoDTO dto) {

        return ResponseEntity.ok(contratoAppService.renovar(id, dto));
    }

    // Finalizar contrato
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<ContratoResponseDTO> finalizarContrato(
            @PathVariable UUID id,
            @Valid @RequestBody(required = false) FinalizarContratoDTO dto) {

        return ResponseEntity.ok(contratoAppService.finalizar(id, dto));
    }

    // Obtener contrato
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> obtenerContrato(@PathVariable UUID id) {
        return ResponseEntity.ok(contratoAppService.obtenerContrato(id));
    }

    // Listar contratos
    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarContratos() {
        return ResponseEntity.ok(contratoAppService.listarContratos());
    }

    // Pago externo proveniente del PSP
    @PostMapping("/{id}/pagos")
    public ResponseEntity<Void> registrarPagoDesdePay(
            @PathVariable UUID id,
            @RequestBody PagoRealizadoDTO dto) {

        contratoAppService.registrarPagoExterno(id, dto);
        return ResponseEntity.ok().build();
    }

}
