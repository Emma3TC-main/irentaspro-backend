package com.irentaspro.pay.infrastructure.adapters.in.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irentaspro.pay.infrastructure.adapters.out.api.IPSPAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Endpoint público para webhooks de PSP.
 */
@RestController
@RequestMapping("/api/webhooks/psp")
@RequiredArgsConstructor
public class PSPWebhookController {

    private static final Logger log = LoggerFactory.getLogger(PSPWebhookController.class);

    private final IPSPAdapter pspAdapter;

    @PostMapping
    public ResponseEntity<Void> recibirWebhook(@RequestBody Map<String, Object> payload) {
        try {
            log.info("[Webhook PSP] Payload recibido: {}", payload);
            pspAdapter.webhook(payload);
            // 202 Accepted es lo habitual para webhooks asíncronos
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            log.error("[Webhook PSP] Error procesando webhook: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}
