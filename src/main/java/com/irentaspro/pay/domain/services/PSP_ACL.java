package com.irentaspro.pay.domain.services;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.common.domain.model.valueobjects.Monto;

/**
 * Capa Anti-Corrupción (Anti-Corruption Layer, ACL) entre el dominio
 * y el sistema externo de pagos (PSP).
 *
 * Su función es traducir estructuras externas (payloads) al lenguaje
 * y modelo del dominio.
 */
public class PSP_ACL {

    /**
     * Convierte el payload recibido del PSP a eventos o modelos de dominio.
     * Aquí se realiza la lógica de traducción del formato externo al interno.
     *
     * @param payload datos recibidos del PSP (por ejemplo, JSON parseado)
     */
    public void mapearRespuesta(Map<String, Object> payload) {
        if (payload == null || payload.isEmpty()) {
            throw new IllegalArgumentException("Payload PSP vacío o inválido");
        }

        try {
            String ref = (String) payload.get("ref");
            String estado = (String) payload.get("status");
            String metodo = (String) payload.get("method");
            String tipo = (String) payload.get("type");
            Double amountValue = Double.parseDouble(payload.get("amount").toString());
            String ticketSUNAT = (String) payload.get("ticket");

            // Simulación: generar un objeto Pago del dominio a partir de la respuesta del
            // PSP
            Pago pago = new Pago(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    new Monto(BigDecimal.valueOf(amountValue), "USD"),
                    metodo,
                    tipo,
                    estado);

            // Si el estado indica confirmación, generar comprobante
            if ("confirmed".equalsIgnoreCase(estado)) {
                ComprobanteFiscal cf = new ComprobanteFiscal("Boleta", "<xml_cpe>", ticketSUNAT);
                pago.generarComprobante(cf);
                pago.confirmar();
            }

            // Registrar invariantes o publicar eventos si aplica
            pago.validarInvariantes();

            System.out.println("[PSP_ACL] Pago procesado exitosamente desde PSP: " + ref);

        } catch (Exception e) {
            System.err.println("[PSP_ACL] Error al mapear respuesta del PSP: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<String, Object> traducirSolicitud(Pago pago) {
        if (pago == null)
            throw new IllegalArgumentException("Pago no puede ser nulo.");

        return Map.of(
                "contratoId", pago.getContratoId() != null ? pago.getContratoId().toString() : null,
                "usuarioId", pago.getUsuarioId() != null ? pago.getUsuarioId().toString() : null,
                "monto", pago.getMonto().valor().toString(),
                "metodo", pago.getMetodo(),
                "tipoPago", pago.getTipoPago(),
                "estado", pago.getEstado());
    }

}
