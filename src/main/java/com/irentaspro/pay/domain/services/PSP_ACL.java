package com.irentaspro.pay.domain.services;

import java.util.Map;

import com.irentaspro.pay.domain.gateway.PSPResponse;

public class PSP_ACL {

    /**
     * Traduce el payload del PSP a PSPResponse (no crea objetos de dominio).
     */
    public PSPResponse mapearRespuesta(Map<String, Object> payload) {
        if (payload == null || payload.isEmpty()) {
            throw new IllegalArgumentException("Payload PSP vacío o inválido");
        }

        String ref = payload.getOrDefault("ref", "").toString();
        String status = payload.getOrDefault("status", "").toString();
        String method = payload.getOrDefault("method", "").toString();
        String type = payload.getOrDefault("type", "").toString();
        String currency = payload.getOrDefault("currency", "USD").toString();
        String ticket = payload.getOrDefault("ticket", "").toString();
        Double amount = null;
        try {
            Object a = payload.get("amount");
            if (a != null)
                amount = Double.parseDouble(a.toString());
        } catch (Exception e) {
            amount = null;
        }

        return new PSPResponse(ref, status, method, type, currency, ticket, amount);
    }

    public Map<String, Object> traducirSolicitudParaPSP(String contratoId, String usuarioId, String monto,
            String metodo, String tipoPago) {
        return Map.of(
                "contratoId", contratoId,
                "usuarioId", usuarioId,
                "monto", monto,
                "metodo", metodo,
                "tipoPago", tipoPago);
    }
}
