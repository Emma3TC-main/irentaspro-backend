package com.irentaspro.pay.infrastructure.adapters.out.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Adaptador hacia SUNAT (placeholder).
 * Implementar cuando integres SUNAT (envío de comprobantes, consulta tickets,
 * etc).
 */
@Component
public class SunatAdapter {

    private static final Logger log = LoggerFactory.getLogger(SunatAdapter.class);

    public void enviarComprobante(String xml) {
        log.info("[SunatAdapter] enviarComprobante() - aún no implementado");
        throw new UnsupportedOperationException("SunatAdapter no implementado");
    }
}
