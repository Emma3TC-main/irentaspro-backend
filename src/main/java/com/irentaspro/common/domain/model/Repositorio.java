package com.irentaspro.common.domain.model;

import java.util.UUID;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T extends Entidad> {
    T guardar(T entidad);

    Optional<T> buscarPorId(UUID id);

    void eliminar(UUID id);

    List<T> buscarTodos();
}
