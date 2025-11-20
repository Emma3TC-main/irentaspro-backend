package com.irentaspro.ct.domain.model.valueobjects;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CuotaContrato {

    private int numero;
    private LocalDate fechaPago;
    private BigDecimal monto;
    private boolean pagado;

    public CuotaContrato(int numero, LocalDate fechaPago, BigDecimal monto) {
        this.numero = numero;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.pagado = false;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void marcarComoPagada() {
        this.pagado = true;
    }
}