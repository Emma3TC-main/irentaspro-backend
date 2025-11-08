package com.irentaspro.pay.domain.model;

import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

public class ComprobanteFiscal extends Entidad {
    private String tipo; // Factura, boleta, etc.
    private String xml; // Representación XML CPE
    private String ticketSUNAT; // Identificador fiscal

    public ComprobanteFiscal(String tipo, String xml, String ticketSUNAT) {
        this.tipo = tipo;
        this.xml = xml;
        this.ticketSUNAT = ticketSUNAT;
    }

    public String getTipo() {
        return tipo;
    }

    public String getXml() {
        return xml;
    }

    public String getTicketSUNAT() {
        return ticketSUNAT;
    }
}
