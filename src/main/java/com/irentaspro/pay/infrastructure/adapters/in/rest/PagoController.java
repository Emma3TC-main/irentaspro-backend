package com.irentaspro.pay.infrastructure.adapters.in.rest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irentaspro.pay.application.command.EmitirComprobanteCommand;
import com.irentaspro.pay.application.command.IniciarPagoCommand;
import com.irentaspro.pay.application.command.handler.IniciarPagoCommandHandler;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.service.PagoApplicationService;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoApplicationService pagoService;
    private final IniciarPagoCommandHandler iniciarPagoHandler;

    /**
     * Registrar un nuevo pago.
     */
    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody PagoDTO dto) {
        // Validaciones básicas antes de delegar a la capa de aplicación
        if (dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
        }
        if (dto.getMoneda() == null || dto.getMoneda().isBlank()) {
            throw new IllegalArgumentException("La moneda es obligatoria.");
        }

        PagoDTO nuevoPago = pagoService.registrarPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Pago registrado correctamente",
                "pago", nuevoPago));
    }

    /**
     * Confirmar un pago ya registrado.
     */
    @PostMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarPago(@PathVariable UUID id) {
        pagoService.confirmarPago(id);
        return ResponseEntity.ok(Map.of("mensaje", "Pago confirmado correctamente"));
    }

    /**
     * Obtener detalles de un pago.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPago(@PathVariable UUID id) {
        PagoDTO pago = pagoService.obtenerPago(id);
        return ResponseEntity.ok(pago);
    }

    /**
     * Iniciar un pago externo (por ejemplo, PayPal).
     */
    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPago(@RequestBody PagoDTO dto) {
        if (dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
        }

        var command = new IniciarPagoCommand(
                dto.getContratoId(),
                dto.getUsuarioId(),
                dto.getMonto(),
                dto.getMoneda(),
                dto.getMetodo(),
                dto.getTipoPago());

        String referenciaPayPal = iniciarPagoHandler.handle(command);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Pago iniciado con éxito",
                "referenciaPayPal", referenciaPayPal));
    }

    /**
     * Emitir el comprobante fiscal de un pago confirmado.
     */
    @PostMapping("/{id}/emitir-comprobante")
    public ResponseEntity<?> emitirComprobante(@PathVariable UUID id, @RequestParam String tipo) {
        var command = new EmitirComprobanteCommand(id, tipo);
        ComprobanteFiscal comprobante = pagoService.emitirComprobante(command);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Comprobante emitido correctamente",
                "comprobante", Map.of(
                        "id", comprobante.getId(),
                        "tipo", comprobante.getTipo(),
                        "ticketSUNAT", comprobante.getTicketSUNAT())));
    }
}
