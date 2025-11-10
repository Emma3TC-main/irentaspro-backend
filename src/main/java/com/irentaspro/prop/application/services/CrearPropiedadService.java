package com.irentaspro.prop.application.services;

import org.springframework.stereotype.Service;

import com.irentaspro.prop.application.dto.PropiedadRequest;
import com.irentaspro.prop.application.dto.PropiedadResponse;
import com.irentaspro.prop.domain.model.Propiedad;
import com.irentaspro.prop.domain.model.valueobjects.Direccion;
import com.irentaspro.prop.domain.model.valueobjects.Precio;
import com.irentaspro.prop.domain.model.valueobjects.Ubicacion;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CrearPropiedadService {

    private final PropiedadRepositorio propiedadRepositorio;

    public CrearPropiedadService(PropiedadRepositorio propiedadRepositorio) {
        this.propiedadRepositorio = propiedadRepositorio;
    }

    @Transactional
    public PropiedadResponse ejecutar(PropiedadRequest request) {
        Direccion direccion = new Direccion(
                request.getCalle(),
                request.getDistrito(),
                request.getProvincia());

        Ubicacion ubicacion = new Ubicacion(
                request.getLatitud(),
                request.getLongitud(),
                request.getDistrito());

        Precio precio = new Precio(request.getPrecio(), request.getMoneda());

        Propiedad propiedad = new Propiedad(
                request.getOwnerId(),
                request.getTitulo(),
                request.getDescripcion(),
                direccion,
                ubicacion,
                precio);

        propiedadRepositorio.guardar(propiedad);
        return PropiedadResponse.fromDomain(propiedad);
    }
}
