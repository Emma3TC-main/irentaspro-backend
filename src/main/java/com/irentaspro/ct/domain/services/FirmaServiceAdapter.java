package com.irentaspro.ct.domain.services;

import java.time.LocalDateTime;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;

public class FirmaServiceAdapter implements IFirmaAdapter {

    @Override
    public FirmaDigital solicitarFirma(Contrato contrato) {
        // Simulación de integración con proveedor externo
        HashDocumento hash = new HashDocumento("abc123hash", "SHA-256");
        return new FirmaDigital("ProveedorX", "CertificadoDigital123", hash, LocalDateTime.now());
    }

    @Override
    public boolean validarFirma(HashDocumento hash) {
        // Lógica de validación del hash (firma)
        return hash != null && hash.valor() != null;
    }

}
