package com.irentaspro.pay.domain.model;

import java.util.Map;

import com.irentaspro.common.domain.model.Entidad;

public class TransaccionPSP extends Entidad {
    private String provider;
    private String ref;
    private Map<String, Object> payload;

    public TransaccionPSP(String provider, String ref, Map<String, Object> payload) {
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("El provider no puede ser nulo o vacío.");
        }
        if (ref == null || ref.isBlank()) {
            throw new IllegalArgumentException("La referencia no puede ser nula o vacía.");
        }
        
        this.provider = provider;
        this.ref = ref;
        this.payload = payload;
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
}
