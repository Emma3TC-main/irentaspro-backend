package com.irentaspro.pay.infrastructure.adapters.out.api;

import java.util.Map;

import com.irentaspro.pay.domain.model.Pago;

/**
 * Puerto de salida que define las operaciones que un adaptador PSP debe
 * ofrecer.
 * 
 * Implementa el principio de Inversión de Dependencias (DIP) dentro de la
 * arquitectura
 * hexagonal, permitiendo intercambiar proveedores de pago sin afectar el
 * dominio.
 */
public interface IPSPAdapter {

    /**
     * Inicia un pago con el PSP externo.
     *
     * @param pago objeto del dominio a traducir y enviar
     * @return respuesta del PSP (generalmente un mapa de datos)
     */
    Map<String, Object> iniciarPago(Pago pago);

    /**
     * Procesa un webhook recibido del PSP (confirmación, error, etc.).
     *
     * @param payload datos del evento en formato clave-valor
     */
    void webhook(Map<String, Object> payload);
}