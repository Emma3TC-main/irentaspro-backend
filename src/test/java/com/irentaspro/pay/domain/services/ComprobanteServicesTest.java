package com.irentaspro.pay.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import java.util.UUID;

class ComprobanteServicesTest {
    
    private ComprobanteService comprobanteServiceTest;
    private Pago pagoTest;

    @BeforeEach
    void setUp(){
        comprobanteServiceTest = new ComprobanteService();
        pagoTest = new Pago(
            UUID.randomUUID()
            , UUID.randomUUID(),
            new Monto(1200.00,"USD")
            , "Paypal", 
            "contrato",
            "confirmado");
    }

    @Test
    void crearUnComprobanteYAsignarloAlPago(){
        assertNull(pagoTest.getComprobanteFiscal(), "El Pago no tiene un Comprobante Fiscal aún");

        ComprobanteFiscal comprobanteFiscalTest = comprobanteServiceTest.emitirCPE(pagoTest);
        assertNotNull(comprobanteFiscalTest, "El comprobante generado no puede ser nulo, se debio integrar un pago");
        assertEquals("Factura", comprobanteFiscalTest.getTipo());
        assertTrue(comprobanteFiscalTest.getTicketSUNAT().startsWith("SUNAT-"), "El ticket debe tener el formato SUNAT-");

        String idPago = pagoTest.getId().toString();
        assertTrue(comprobanteFiscalTest.getXml().contains(idPago), "El XML debe contener el ID de Pago");

        assertNotNull(pagoTest.getComprobanteFiscal(), "El pago debería tener un comprobante fiscal");

        assertEquals(comprobanteFiscalTest, pagoTest.getComprobanteFiscal(), "El comprobante que se generó esta asignado a otro pago");
    } 
}
