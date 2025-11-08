package com.irentaspro.pay.domain.services;

import java.util.UUID;

import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.pay.domain.model.Pago;

public class ComprobanteService {
    public ComprobanteFiscal emitirCPE(Pago pago) {
        String xml = "<CPE><Pago>" + pago.getId() + "</Pago></CPE>";
        String ticket = "SUNAT-" + UUID.randomUUID().toString().substring(0, 8);

        ComprobanteFiscal cf = new ComprobanteFiscal(
                "Factura",
                xml,
                ticket);

        pago.generarComprobante(cf);
        return cf;
    }
}
