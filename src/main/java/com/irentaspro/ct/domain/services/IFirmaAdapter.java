package com.irentaspro.ct.domain.services;

import java.util.UUID;
import com.irentaspro.ct.domain.model.FirmaDigital;
import com.irentaspro.common.domain.model.valueobjects.HashDocumento;

/**
 * Puerto de salida del dominio.
 * Permite abstraer la firma digital (infraestructura o servicio externo).
 */
public interface IFirmaAdapter {

    /**
     * Firma un documento digitalmente usando un proveedor externo.
     * 
     * @param contratoId    ID del contrato a firmar
     * @param hashDocumento Hash y algoritmo del documento
     * @return Objeto FirmaDigital con los metadatos generados
     */
    FirmaDigital firmarDocumento(UUID contratoId, HashDocumento hashDocumento);
}
