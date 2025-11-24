package com.irentaspro.pay.infrastructure.adapters.out.api;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PSP_ACL;
import com.irentaspro.pay.domain.services.PagoService;
import com.irentaspro.pay.infrastructure.adapters.out.gateway.PaypalGatewayAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Implementación concreta del adaptador PSP.
 * PSP_ACL debe ser un bean para facilitar testing/configuración.
 */
@Component
@RequiredArgsConstructor
public class PSPAdapterImpl implements IPSPAdapter {

    private static final Logger log = LoggerFactory.getLogger(PSPAdapterImpl.class);

    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;
    private final PaypalGatewayAdapter paypalGateway;

    @Override
    public Map<String, Object> iniciarPago(Pago pago) {
        return Map.of(); // Ahora solo manejamos pago vía PayPal directamente en handler
    }

    @Override
    public void webhook(Map<String, Object> payload) {
        log.info("[PSPAdapter] Webhook recibido: {}", payload);

        // Extraemos orderId de PayPal
        String orderId = (String) payload.get("resource.id");
        String status = (String) payload.get("resource.status");

        if (orderId == null) {
            log.error("Webhook PayPal sin orderId");
            return;
        }

        Pago pago = pagoRepositorio.buscarPorReferenciaExterna(orderId)
                .orElseThrow(() -> new IllegalStateException("Pago no encontrado para referencia " + orderId));

        if ("COMPLETED".equalsIgnoreCase(status)) {
            pagoService.confirmar(pago);
            pagoRepositorio.guardar(pago);
            log.info("Pago confirmado por webhook PayPal: {}", pago.getId());
        } else {
            log.warn("Pago PayPal con status no completado: {}", status);
        }
    }
}
