package com.irentaspro.prop.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;

@Service
public class ListarTodasPropiedadesService {

    private final PropiedadRepositorio propiedadRepositorio;

    public ListarTodasPropiedadesService(PropiedadRepositorio propiedadRepositorio) {
        this.propiedadRepositorio = propiedadRepositorio;
    }

    public List<PropiedadResponse> ejecutar() {
        return propiedadRepositorio.buscarTodos()
                .stream()
                .map(PropiedadResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
