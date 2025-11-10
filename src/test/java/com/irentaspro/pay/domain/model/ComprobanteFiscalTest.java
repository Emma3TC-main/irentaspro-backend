package com.irentaspro.pay.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class ComprobanteFiscalTest {   
    
    private ComprobanteFiscal comprobanteFiscalTest;

    //Test Forzando el error
    @Test
    void crearComprobanteFiscalValido(){
        //1. Arrange
        String tipo = "Factura";
        String xml = "<xml>...</xml>";
        String ticketSunat = "123456789";
        //2. Act
        comprobanteFiscalTest = new ComprobanteFiscal(tipo, xml, ticketSunat);
        //3. Assert
        assertNotNull(comprobanteFiscalTest);
        assertEquals(tipo, comprobanteFiscalTest.getTipo());
        assertEquals(xml, comprobanteFiscalTest.getXml());
        assertEquals(ticketSunat, comprobanteFiscalTest.getTicketSUNAT());
    }

    @Test
    void errorTipoNull(){
        assertThrows(IllegalArgumentException.class, () -> {
        comprobanteFiscalTest = new ComprobanteFiscal(null, "<xml>...<xml>", "123549498");
        });
    }

    @Test
    void errorXmlNull(){
        assertThrows(IllegalArgumentException.class,()-> {
        comprobanteFiscalTest = new ComprobanteFiscal("Factura", null, "121651888");
        });
    }

    @Test
    void errorTicketSunatNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            comprobanteFiscalTest = new ComprobanteFiscal ("Factura","<xml>...<xml>", null);
        });
    }

       @Test
    void errorTipoVacio(){
        assertThrows(IllegalArgumentException.class, () -> {
        comprobanteFiscalTest = new ComprobanteFiscal("", "<xml>...<xml>", "123549498");
        });
    }

    @Test
    void errorXmlVacio(){
        assertThrows(IllegalArgumentException.class,()-> {
        comprobanteFiscalTest = new ComprobanteFiscal("Factura", "", "121651888");
        });
    }

    @Test
    void errorTicketSunatVacio(){
        assertThrows(IllegalArgumentException.class, () -> {
            comprobanteFiscalTest = new ComprobanteFiscal ("Factura","<xml>...<xml>", "");
        });
    }

     
    
}
