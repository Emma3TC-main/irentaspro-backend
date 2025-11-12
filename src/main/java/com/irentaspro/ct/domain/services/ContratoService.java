package com.irentaspro.ct.domain.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

/**
 * Servicio de dominio para operaciones de negocio del agregado Contrato.
 * 
 * Encapsula la lógica de firma digital, delegando la integración
 * a un adaptador (IFirmaAdapter).
 */
@Service
public class ContratoService {

    private final IFirmaAdapter firmaAdapter;

    public ContratoService(IFirmaAdapter firmaAdapter) {
        this.firmaAdapter = firmaAdapter;
    }

    /**
     * Firma digitalmente un contrato.
     * Aplica las reglas de negocio del dominio y usa el adaptador de firma.
     */
    public void firmarContrato(Contrato contrato) {
        // Se obtiene la firma desde el servicio externo (adaptador)
        FirmaDigital firma = firmaAdapter.firmarDocumento(
                contrato.getId(),
                new HashDocumento("hash-simulacion-" + UUID.randomUUID(), "SHA-256")
        );

        // Se delega al agregado la validación del estado y la aplicación de la firma
        contrato.firmar(firma);
    }
}
