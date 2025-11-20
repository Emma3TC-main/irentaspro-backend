package com.irentaspro.ct.domain.model.events;

import java.util.UUID;
import com.irentaspro.common.domain.model.DomainEvent;

public class ContratoFirmado extends DomainEvent {

    private final UUID contratoId;

    public ContratoFirmado(UUID contratoId) {
        super("contrato.firmado");
        this.contratoId = contratoId;
    }

    public UUID getContratoId() {
        return contratoId;
    }
}
