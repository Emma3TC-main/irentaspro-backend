package com.irentaspro.prop.application.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;

@Service
public class ListarPropiedadesService {

    private final PropiedadRepositorio propiedadRepositorio;

    public ListarPropiedadesService(PropiedadRepositorio propiedadRepositorio) {
        this.propiedadRepositorio = propiedadRepositorio;
    }

    public List<PropiedadResponse> ejecutar(UUID ownerId) {
        return propiedadRepositorio.findByOwnerId(ownerId)
                .stream()
                .map(PropiedadResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
