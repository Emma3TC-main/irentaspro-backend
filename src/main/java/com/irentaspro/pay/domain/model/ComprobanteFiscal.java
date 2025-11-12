package com.irentaspro.pay.domain.model;

import java.util.UUID;
import com.irentaspro.common.domain.model.Entidad;

/**
 * Entidad de dominio: ComprobanteFiscal
 * Representa un comprobante fiscal (boleta, factura, etc.) emitido tras la
 * confirmación de un pago.
 * Es una entidad del dominio asociada a un Pago.
 */
public class ComprobanteFiscal extends Entidad {

    private final String tipo; // Ejemplo: "Boleta", "Factura"
    private final String xml; // Representación XML del comprobante (CPE)
    private final String ticketSUNAT; // Identificador fiscal asignado por SUNAT

    /**
     * Constructor principal: crea un nuevo comprobante fiscal.
     */
    public ComprobanteFiscal(String tipo, String xml, String ticketSUNAT) {
        super(); // genera UUID automáticamente desde la clase base Entidad
        this.tipo = tipo;
        this.xml = xml;
        this.ticketSUNAT = ticketSUNAT;
        validarInvariantes();
    }

    /**
     * Constructor utilizado para reconstrucción desde persistencia.
     */
    public ComprobanteFiscal(UUID id, String tipo, String xml, String ticketSUNAT) {
        super(id);
        this.tipo = tipo;
        this.xml = xml;
        this.ticketSUNAT = ticketSUNAT;
        validarInvariantes();
    }

    /**
     * Constructor de dominio especializado que genera el comprobante a partir del
     * pago
     */

    public ComprobanteFiscal(Pago pago, String tipo) {
        super(); // genera UUID
        this.tipo = tipo;
        this.ticketSUNAT = "TCK-" + pago.getId().toString().substring(0, 8); // simulación de ticket
        this.xml = "<Comprobante id='" + this.id + "' tipo='" + tipo + "' pago='" + pago.getId() + "'/>";
        validarInvariantes();
    }


    
    // --- Getters ---
    public String getTipo() {
        return tipo;
    }

    public String getXml() {
        return xml;
    }

    public String getTicketSUNAT() {
        return ticketSUNAT;
    }

    // --- Validación de invariantes ---
    @Override
    public void validarInvariantes() {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de comprobante no puede estar vacío.");
        }
        if (ticketSUNAT == null || ticketSUNAT.isBlank()) {
            throw new IllegalArgumentException("El ticket SUNAT es obligatorio.");
        }
        if (xml == null || xml.isBlank()) {
            throw new IllegalArgumentException("El XML del comprobante no puede estar vacío.");
        }
    }
}
