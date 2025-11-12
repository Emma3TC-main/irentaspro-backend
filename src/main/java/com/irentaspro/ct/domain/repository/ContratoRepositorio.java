package com.irentaspro.ct.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.irentaspro.common.domain.model.Repositorio;
import com.irentaspro.ct.domain.model.Contrato;

public interface ContratoRepositorio extends Repositorio<Contrato> {
    @Override
    Contrato guardar(Contrato contrato);

    @Override
    Optional<Contrato> buscarPorId(UUID id);

    @Override
    List<Contrato> buscarTodos();
}
