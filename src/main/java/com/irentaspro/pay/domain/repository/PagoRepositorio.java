package com.irentaspro.pay.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.irentaspro.common.domain.model.Repositorio;
import com.irentaspro.pay.domain.model.Pago;

public interface PagoRepositorio extends Repositorio<Pago> {

    @Override
    Pago guardar(Pago pago);

    @Override
    Optional<Pago> buscarPorId(UUID id);

    @Override
    void eliminar(UUID id);

    // Métodos adicionales específicos del dominio PAY
    // Optional<Pago> buscarPorReferencia(String referencia);
}
