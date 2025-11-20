package com.irentaspro.ct.domain.model.events;

import java.util.List;
import java.util.UUID;

import com.irentaspro.common.domain.model.DomainEvent;
import com.irentaspro.ct.domain.model.valueobjects.CuotaContrato;

public class CalendarioPagosGenerado extends DomainEvent {

    private final UUID contratoId;
    private final List<CuotaContrato> cuotas;

    public CalendarioPagosGenerado(UUID contratoId, List<CuotaContrato> cuotas) {
        super("contrato.calendario.generado");
        this.contratoId = contratoId;
        this.cuotas = cuotas;
    }

    public UUID getContratoId() {
        return contratoId;
    }

    public List<CuotaContrato> getCuotas() {
        return cuotas;
    }
}
