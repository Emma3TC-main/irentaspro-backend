package com.irentaspro.pay.domain.model;

import java.util.UUID;

import com.irentaspro.common.domain.model.Entidad;

public class ComprobanteFiscal extends Entidad {
    private String tipo; // Factura, boleta, etc.
    private String xml; // Representación XML CPE
    private String ticketSUNAT; // Identificador fiscal

    public ComprobanteFiscal(String tipo, String xml, String ticketSUNAT) {
        
        //Aplicando TDD test -> comprobanteFiscalNUll
        if (tipo == null || xml == null || ticketSUNAT == null){
            throw new IllegalArgumentException("El comprobante fiscal no puede tener atributos nulos.");   
        }
        
        if (tipo.isEmpty() || xml.isEmpty() || ticketSUNAT.isEmpty()) {
            throw new IllegalArgumentException("El comprobante fiscal no puede tene atributos vacios");
        }
        
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
