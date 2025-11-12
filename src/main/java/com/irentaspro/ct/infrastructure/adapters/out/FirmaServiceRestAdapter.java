package com.irentaspro.ct.infrastructure.adapters.out;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.ct.domain.services.IFirmaAdapter;

import java.util.UUID;

@Component
public class FirmaServiceRestAdapter implements IFirmaAdapter {

    @Override
    public FirmaDigital firmarDocumento(UUID contratoId, HashDocumento hashDocumento) {
        // Simula el consumo de un servicio REST de firma
        return new FirmaDigital(
                "FirmaCloud",
                "CERT-" + contratoId.toString().substring(0, 8),
                hashDocumento,
                LocalDateTime.now());
    }
}
