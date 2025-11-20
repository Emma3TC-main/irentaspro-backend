package com.irentaspro.ct.domain.services;

import java.util.UUID;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

public class ContratoService {

    private final IFirmaAdapter firmaAdapter;

    public ContratoService(IFirmaAdapter firmaAdapter) {
        this.firmaAdapter = firmaAdapter;
    }

    public void firmarContrato(Contrato contrato) {
        if (contrato == null)
            throw new IllegalArgumentException("Contrato es requerido");

        HashDocumento hash = new HashDocumento(
                "hash-" + contrato.getId().toString(),
                "SHA-256");

        FirmaDigital firma = firmaAdapter.firmarDocumento(contrato.getId(), hash);
        contrato.firmar(firma);
    }
}
