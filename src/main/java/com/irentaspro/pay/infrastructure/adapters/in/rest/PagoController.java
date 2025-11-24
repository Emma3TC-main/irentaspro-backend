package com.irentaspro.pay.infrastructure.adapters.in.rest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import com.irentaspro.pay.application.command.EmitirComprobanteCommand;
import com.irentaspro.pay.application.command.IniciarPagoCommand;
import com.irentaspro.pay.application.command.IniciarPagoPremiumCommand;
import com.irentaspro.pay.application.command.handler.IniciarPagoCommandHandler;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.pay.domain.model.Pago;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Validated
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    private final PagoApplicationService pagoService;
    private final IniciarPagoCommandHandler iniciarPagoHandler;
    private final com.irentaspro.pay.application.command.handler.IniciarPagoPremiumCommandHandler iniciarPagoPremiumHandler;
    private final com.irentaspro.pay.domain.repository.PagoRepositorio pagoRepositorio;
    private final com.irentaspro.iam.application.service.AuthApplicationService usuarioService;

    private final com.irentaspro.pay.infrastructure.adapters.out.gateway.PaypalGatewayAdapter paypalGateway;

    // --- Pagos normales ---
    @PostMapping
    public ResponseEntity<?> registrarPago(@Valid @RequestBody PagoDTO dto) {
        log.info("[PagoController] registrando pago contrato={} usuario={}", dto.getContratoId(), dto.getUsuarioId());
        PagoDTO nuevoPago = pagoService.registrarPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Pago registrado correctamente",
                "pago", nuevoPago));
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarPago(@PathVariable UUID id) {
        pagoService.confirmarPago(id);
        return ResponseEntity.ok(Map.of("mensaje", "Pago confirmado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPago(@PathVariable UUID id) {
        PagoDTO pago = pagoService.obtenerPago(id);
        return ResponseEntity.ok(pago);
    }

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPago(@Valid @RequestBody IniciarPagoRequest dto) {
        log.info("[PagoController] iniciar pago contrato={} usuario={} monto={}", dto.getContratoId(),
                dto.getUsuarioId(), dto.getMonto());
        try {
            var command = new IniciarPagoCommand(dto.getContratoId(), dto.getUsuarioId(), dto.getMonto(),
                    dto.getMoneda(), dto.getMetodo(), dto.getTipoPago());
            String referenciaPayPal = iniciarPagoHandler.handle(command);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Pago iniciado con éxito",
                    "referenciaPayPal", referenciaPayPal));
        } catch (Exception e) {
            log.error("[PagoController] Error iniciando pago: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of("error", "Error iniciando pago: " + e.getMessage()));
        }
    }

    // --- Pagos PREMIUM ---
    @PostMapping("/premium/iniciar")
    public ResponseEntity<?> iniciarPagoPremium(@Valid @RequestBody PremiumPagoRequest request) {
        log.info("[PagoController] iniciar pago PREMIUM usuario={} monto={}", request.getUsuarioId(),
                request.getMonto());

        var command = new IniciarPagoPremiumCommand(
                request.getUsuarioId(),
                request.getMonto(),
                request.getMoneda(),
                "PayPal" // opcional, puedes dejar null también y el handler usa el default
        );

        PagoDTO pagoPremium = iniciarPagoPremiumHandler.handle(command);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Pago Premium iniciado",
                "referenciaPayPal", pagoPremium.getReferenciaExterna()));
    }

    @PostMapping("/premium/capturar")
    public ResponseEntity<?> capturarPagoPremium(@RequestParam("token") String token) {
        log.info("[PagoController] capturando pago PREMIUM PayPal token={}", token);

        // 1. Capturar orden PayPal
        boolean ok = paypalGateway.capturarOrden(token);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudo capturar el pago en PayPal"));
        }

        // 2. Buscar pago por referencia externa
        Pago pago = pagoRepositorio.buscarPorReferenciaExterna(token)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado para token=" + token));

        // 3. Confirmar pago en el dominio
        pagoService.confirmarPago(pago.getId());

        // 4. Activar Premium para el usuario
        usuarioService.upgradeCuenta(pago.getUsuarioId());

        log.info("[PagoController] Pago PREMIUM confirmado y cuenta actualizada usuario={}",
                pago.getUsuarioId());

        return ResponseEntity.ok(Map.of("mensaje", "Cuenta Premium activada"));
    }

    @PostMapping("/premium/confirmar")
    public ResponseEntity<?> confirmarPagoPremium(@RequestBody Map<String, String> request) {
        String referenciaPayPal = request.get("referenciaPayPal");

        Pago pago = pagoRepositorio.buscarPorReferenciaExterna(referenciaPayPal)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        // Confirmar pago y actualizar usuario a PREMIUM
        pagoService.confirmarPago(pago.getId());
        usuarioService.upgradeCuenta(pago.getUsuarioId());

        return ResponseEntity.ok(Map.of("mensaje", "Cuenta Premium activada"));
    }

    // --- Comprobantes ---
    @PostMapping("/{id}/emitir-comprobante")
    public ResponseEntity<?> emitirComprobante(@PathVariable UUID id, @RequestParam String tipo) {
        ComprobanteFiscal comprobante = pagoService.emitirComprobante(new EmitirComprobanteCommand(id, tipo));
        return ResponseEntity.ok(Map.of(
                "mensaje", "Comprobante emitido correctamente",
                "comprobante", Map.of(
                        "id", comprobante.getId(),
                        "tipo", comprobante.getTipo(),
                        "ticketSUNAT", comprobante.getTicketSUNAT())));
    }

    // --- DTOs ---
    @Data
    public static class IniciarPagoRequest {
        @NotNull
        private UUID contratoId;
        @NotNull
        private UUID usuarioId;
        @NotNull
        @Positive(message = "El monto debe ser mayor que 0")
        private BigDecimal monto;
        @NotNull
        private String moneda;
        private String metodo;
        private String tipoPago;
    }

    @Data
    public static class PremiumPagoRequest {
        @NotNull
        private UUID usuarioId;
        @NotNull
        @Positive(message = "El monto debe ser mayor que 0")
        private BigDecimal monto;
        @NotNull
        private String moneda;
    }
}
