package com.irentaspro.pay.infrastructure.adapters.out.api;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.services.PSP_ACL;

/**
 * Adaptador concreto que implementa la comunicación con el Proveedor de
 * Servicios de Pago (PSP). Actúa como un puerto de salida (adapter out).
 *
 * Simula el envío y recepción de información entre el dominio
 * y un servicio externo (como Stripe, Niubiz, etc.).
 */
@Component
public class PSPAdapter implements IPSPAdapter {

    private final PSP_ACL acl = new PSP_ACL();

    /**
     * Inicia un pago a través del PSP, traduciendo el modelo del dominio
     * al formato requerido por el proveedor.
     *
     * @param pago instancia del dominio que representa el pago a procesar
     * @return mapa con los datos simulados devueltos por el PSP (status, ref)
     */
    public Map<String, Object> iniciarPago(Pago pago) {
        Map<String, Object> solicitud = acl.traducirSolicitud(pago);

        // 🔹 Simulación: envío de la solicitud al PSP externo
        System.out.println("[PSPAdapter] Enviando solicitud de pago al PSP...");
        System.out.println("[PSPAdapter] Payload: " + solicitud);

        // 🔹 Simulación: respuesta del PSP
        String ref = UUID.randomUUID().toString();
        Map<String, Object> respuesta = Map.of(
                "status", "ok",
                "ref", ref);

        System.out.println("[PSPAdapter] PSP respondió: " + respuesta);
        return respuesta;
    }

    /**
     * Procesa un webhook recibido del PSP y lo adapta al lenguaje del dominio.
     *
     * @param payload datos crudos enviados por el PSP (generalmente en JSON)
     */
    @Override
    public void webhook(Map<String, Object> payload) {
        System.out.println("[PSPAdapter] Recibiendo webhook del PSP...");
        acl.mapearRespuesta(payload);
    }
}
