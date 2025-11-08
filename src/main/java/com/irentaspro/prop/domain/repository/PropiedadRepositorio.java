package com.irentaspro.prop.domain.repository;

import java.util.List;
import java.util.UUID;

import com.irentaspro.common.domain.model.Repositorio;
import com.irentaspro.prop.domain.model.Propiedad;

public interface PropiedadRepositorio extends Repositorio<Propiedad> {
    List<Propiedad> buscarPorPropietario(UUID ownerId);
}
