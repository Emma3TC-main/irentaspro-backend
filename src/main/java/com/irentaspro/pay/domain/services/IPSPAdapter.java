package com.irentaspro.pay.domain.services;

import java.util.Map;

import com.irentaspro.pay.domain.model.Pago;

public interface IPSPAdapter {
    Map<String, Object> iniciarPago(Pago pago);

    void webhook(Map<String, Object> payload);
}
