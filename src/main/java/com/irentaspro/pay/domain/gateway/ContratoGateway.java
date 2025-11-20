package com.irentaspro.pay.domain.gateway;

import java.util.Optional;
import java.util.UUID;


public interface ContratoGateway {
    Optional<ContratoDTO> obtenerContrato(UUID contratoId);

    void registrarPagoEnContrato(UUID contratoId, PagoRealizadoDTO pago);
}
