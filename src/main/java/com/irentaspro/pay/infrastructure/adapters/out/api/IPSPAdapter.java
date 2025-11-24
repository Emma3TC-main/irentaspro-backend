package com.irentaspro.pay.infrastructure.adapters.out.api;

import java.util.Map;

import com.irentaspro.pay.domain.model.Pago;

/**
 * Puerto de salida para adaptadores de PSP.
 */
public interface IPSPAdapter {

    /**
     * Inicia un pago con el PSP externo.
     * 
     * @param pago objeto del dominio
     * @return respuesta del PSP (mapa)
     */
    Map<String, Object> iniciarPago(Pago pago);

    /**
     * Procesa un webhook recibido del PSP.
     *
     * @param payload datos del evento
     */
    void webhook(Map<String, Object> payload);
}
