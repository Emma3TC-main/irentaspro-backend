package com.irentaspro.ct.domain.services;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.FirmaDigital;

public interface IFirmaAdapter {

    boolean validarFirma(HashDocumento hash);

    FirmaDigital solicitarFirma(Contrato contrato);
}
