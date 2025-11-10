package com.irentaspro.common.domain.model;

import java.util.UUID;

import com.irentaspro.bi.domain.model.Reporte;

import java.util.Optional;

public interface Repositorio<T extends Entidad> {
    T guardar(T entidad);

    Optional<T> buscarPorId(UUID id);

    void eliminar(UUID id);
}
