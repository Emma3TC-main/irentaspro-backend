package com.irentaspro.pay.application.command;

import java.util.List;

import com.irentaspro.pay.domain.model.TransaccionPSP;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ConciliarPagoCommand {
    List<TransaccionPSP> transacciones;
}
