package com.irentaspro.pay.domain.model;

import java.util.UUID;
import com.irentaspro.common.domain.model.Entidad;

public class ComprobanteFiscal extends Entidad {

    private final String tipo;
    private final String xml;
    private final String ticketSUNAT;

    public ComprobanteFiscal(String tipo, String xml, String ticketSUNAT) {
        super();
        this.tipo = tipo;
        this.xml = xml;
        this.ticketSUNAT = ticketSUNAT;
        validarInvariantes();
    }

    public ComprobanteFiscal(UUID id, String tipo, String xml, String ticketSUNAT) {
        super(id);
        this.tipo = tipo;
        this.xml = xml;
        this.ticketSUNAT = ticketSUNAT;
        validarInvariantes();
    }

    public ComprobanteFiscal(Pago pago, String tipo) {
        super();
        this.tipo = tipo;
        this.ticketSUNAT = "TCK-" + pago.getId().toString().substring(0, 8);
        this.xml = "<Comprobante id='" + this.getId() + "' tipo='" + tipo + "' pago='" + pago.getId() + "'/>";
        validarInvariantes();
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

    @Override
    public void validarInvariantes() {
        if (tipo == null || tipo.isBlank())
            throw new IllegalArgumentException("El tipo no puede ser vacío.");
        if (ticketSUNAT == null || ticketSUNAT.isBlank())
            throw new IllegalArgumentException("El ticket SUNAT es obligatorio.");
        if (xml == null || xml.isBlank())
            throw new IllegalArgumentException("El XML del comprobante no puede estar vacío.");
    }
}
