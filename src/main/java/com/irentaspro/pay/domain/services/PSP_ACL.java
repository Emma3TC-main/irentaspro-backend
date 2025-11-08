package com.irentaspro.pay.domain.services;

import java.util.Map;

import com.irentaspro.pay.domain.model.Pago;

public class PSP_ACL {
    public Map<String, Object> traducirSolicitud(Pago pago) {
        return Map.of(
                "monto", pago.getMonto().valor(),
                "moneda", pago.getMonto().moneda(),
                "referencia", pago.getId().toString(),
                "metodo", pago.getMetodo());
    }

    public void mapearRespuesta(Map<String, Object> response) {
        // Aquí se podría transformar o validar la respuesta del PSP
        System.out.println("Respuesta PSP recibida: " + response);
    }
}
