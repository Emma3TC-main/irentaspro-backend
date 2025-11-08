package com.irentaspro.ct.domain.services;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.ct.domain.model.Contrato;

public class ContratoService implements ServiciosDominio {

    private final IFirmaAdapter firmaAdapter;

    public ContratoService(IFirmaAdapter firmaAdapter) {
        this.firmaAdapter = firmaAdapter;
    }

    public void firmar(Contrato contrato) {
        var resultado = firmaAdapter.solicitarFirma(contrato);
        contrato.firmar(resultado);
    }

    public boolean validarEstado(Contrato contrato) {
        return contrato.getEstado() != null && !contrato.getEstado().getEstado().isBlank();
    }

    @Override
    public void ejecutar() {
        // Método genérico de ejecución de servicio de dominio
    }
}
