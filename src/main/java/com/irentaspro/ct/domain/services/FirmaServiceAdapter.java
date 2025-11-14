package com.irentaspro.ct.domain.services;

import java.time.LocalDateTime;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;

public class FirmaServiceAdapter implements IFirmaAdapter {

    @Override
    public FirmaDigital solicitarFirma(Contrato contrato) {
        // Simulación de integración con proveedor externo

        if (contrato == null) {
            throw new IllegalArgumentException("El 'contrato' a firmar no puede ser nulo.");
        }

        HashDocumento hash = new HashDocumento("abc123hash", "SHA-256");
        return new FirmaDigital("ProveedorX", "CertificadoDigital123", hash, LocalDateTime.now());
    }

    @Override
    public boolean validarFirma(HashDocumento hash) {
        // Lógica de validación del hash (firma)

        if (hash == null) {
            throw new IllegalArgumentException("El 'hash' a validar no puede ser nulo.");
        }

        return hash.valor() != null;
    }

}
