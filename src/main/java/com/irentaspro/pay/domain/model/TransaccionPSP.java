package com.irentaspro.pay.domain.model;

import java.util.Map;
import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

/**
 * Representa una transacción procesada por un Proveedor de Servicios de Pago
 * (PSP),
 * como PayPal, Stripe, Culqi, MercadoPago, etc.
 */
public class TransaccionPSP extends Entidad {

    private final String provider; // Nombre del PSP (ej. "Stripe", "Culqi")
    private final String ref; // Identificador externo del PSP
    private final Map<String, Object> payload; // Datos crudos devueltos por el PSP

    public TransaccionPSP(String provider, String ref, Map<String, Object> payload) {
        super();
        this.provider = provider;
        this.ref = ref;
        this.payload = payload;
        validarInvariantes();
    }

    public TransaccionPSP(UUID id, String provider, String ref, Map<String, Object> payload) {
        super(id);
        this.provider = provider;
        this.ref = ref;
        this.payload = payload;
        validarInvariantes();
    }

    public String getProvider() {
        return provider;
    }

    public String getRef() {
        return ref;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    @Override
    public void validarInvariantes() {
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("El proveedor de pago (provider) no puede estar vacío.");
        }
        if (ref == null || ref.isBlank()) {
            throw new IllegalArgumentException("La referencia externa (ref) es obligatoria.");
        }
    }
}
