package com.irentaspro.pay.domain.services;

import java.util.Map;
import java.util.UUID;

import com.irentaspro.pay.domain.model.Pago;

public class PSPAdapter implements IPSPAdapter {
    private final PSP_ACL acl = new PSP_ACL();

    @Override
    public Map<String, Object> iniciarPago(Pago pago) {
        Map<String, Object> solicitud = acl.traducirSolicitud(pago);
        // Simulación: enviar solicitud al proveedor
        return Map.of("status", "ok", "ref", UUID.randomUUID().toString());
    }

    @Override
    public void webhook(Map<String, Object> payload) {
        acl.mapearRespuesta(payload);
    }
}
