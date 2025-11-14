package com.irentaspro.ct.domain.services;

import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.ct.domain.model.Contrato;

public class ContratoService implements ServiciosDominio {

    private final IFirmaAdapter firmaAdapter;

    public ContratoService(IFirmaAdapter firmaAdapter) {
        //TDD
        if (firmaAdapter == null) {
            throw new IllegalArgumentException("El 'firmaAdapter' no puede ser nulo.");
        }

        this.firmaAdapter = firmaAdapter;
    }

    public void firmar(Contrato contrato) {
       
        if (contrato == null) {
            throw new IllegalArgumentException("El 'contrato' a firmar no puede ser nulo.");
        }

        var resultado = firmaAdapter.solicitarFirma(contrato);
        contrato.firmar(resultado);
    }

    public boolean validarEstado(Contrato contrato) {
        if (contrato == null) {
            throw new IllegalArgumentException("El 'contrato' a validar no puede ser nulo.");
        }
        
        return contrato.getEstado() != null && !contrato.getEstado().getEstado().isBlank();
    }

    @Override
    public void ejecutar() {
        // Método genérico de ejecución de servicio de dominio
    }
}
