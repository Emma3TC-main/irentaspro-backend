package com.irentaspro.pay.domain.gateway;

import java.math.BigDecimal;

public interface PasarelaPagoGateway {
    /**
     * Procesa un pago en el PSP (ej. PayPal)
     * 
     * @return referencia o ID de transacción
     */
    String procesarPago(BigDecimal monto, String moneda, String metodo, String referencia);
}
