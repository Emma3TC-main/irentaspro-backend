package com.irentaspro.pay.infrastructure.adapters.in.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irentaspro.pay.infrastructure.adapters.out.api.IPSPAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para recibir webhooks del PSP (Proveedor de Servicios de
 * Pago).
 * Este endpoint es invocado externamente por el PSP cuando se confirma o
 * actualiza un pago.
 *
 * Flujo: PSP externo → (Webhook HTTP POST) → PSPWebhookController → PSPAdapter
 * → Dominio (Pago)
 */
@RestController
@RequestMapping("/api/webhooks/psp")
@RequiredArgsConstructor
public class PSPWebhookController {

    private static final Logger log = LoggerFactory.getLogger(PSPWebhookController.class);

    private final IPSPAdapter pspAdapter;

    /**
     * Recibe el webhook desde el PSP y lo delega al adaptador correspondiente.
     *
     * @param payload datos JSON enviados por el PSP (mapeados automáticamente a
     *                Map)
     * @return 200 OK si el webhook fue procesado correctamente.
     */
    @PostMapping
    public ResponseEntity<Void> recibirWebhook(@RequestBody Map<String, Object> payload) {
        try {
            log.info("[Webhook PSP] Payload recibido: {}", payload);
            pspAdapter.webhook(payload);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {
            log.error("[Webhook PSP] Error procesando webhook: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
