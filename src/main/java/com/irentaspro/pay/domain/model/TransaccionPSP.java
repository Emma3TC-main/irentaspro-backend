package com.irentaspro.pay.domain.model;

import java.util.Map;
import java.util.UUID;
import com.irentaspro.common.domain.model.Entidad;

public class TransaccionPSP extends Entidad {

    private final String provider;
    private final String ref;
    private final Map<String, Object> payload;

    public TransaccionPSP(String provider, String ref, Map<String, Object> payload) {
        super();
        this.provider = provider;
        this.ref = ref;
        this.payload = payload != null ? payload : Map.of();
        validarInvariantes();
    }

    public TransaccionPSP(UUID id, String provider, String ref, Map<String, Object> payload) {
        super(id);
        this.provider = provider;
        this.ref = ref;
        this.payload = payload != null ? payload : Map.of();
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
        if (provider == null || provider.isBlank())
            throw new IllegalArgumentException("Provider obligatorio.");
        if (ref == null || ref.isBlank())
            throw new IllegalArgumentException("Ref obligatorio.");
    }
}
