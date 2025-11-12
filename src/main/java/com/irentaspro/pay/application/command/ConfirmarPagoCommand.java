package com.irentaspro.pay.application.command;

import java.util.UUID;

import lombok.Value;

@Value
public class ConfirmarPagoCommand {
    UUID pagoId;
}
