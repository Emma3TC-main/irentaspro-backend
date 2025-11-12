package com.irentaspro.pay.application.command;

import java.util.UUID;

import lombok.Value;

@Value
public class EmitirComprobanteCommand {
    UUID pagoId;
    String tipo; // FACTURA o BOLETA
}
