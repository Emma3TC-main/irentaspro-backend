package com.irentaspro.prop.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.irentaspro.prop.application.dto.PropiedadRequest;
import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.model.Propiedad;
import com.irentaspro.prop.domain.model.valueobjects.Precio;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ActualizarPropiedadService {
    private final PropiedadRepositorio propiedadRepositorio;

    public ActualizarPropiedadService(PropiedadRepositorio propiedadRepositorio) {
        this.propiedadRepositorio = propiedadRepositorio;
    }

    @Transactional
    public PropiedadResponse ejecutar(UUID id, PropiedadRequest request) {
        Propiedad propiedad = propiedadRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Propiedad no encontrada"));

        propiedad.actualizar(
                request.getTitulo(),
                request.getDescripcion(),
                new Precio(request.getPrecio(), request.getMoneda()));

        propiedadRepositorio.guardar(propiedad);
        return PropiedadResponse.fromDomain(propiedad);
    }
}
