package com.irentaspro.prop.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.irentaspro.prop.domain.model.Propiedad;
import com.irentaspro.prop.domain.model.valueobjects.Direccion;
import com.irentaspro.prop.domain.model.valueobjects.Precio;
import com.irentaspro.prop.domain.model.valueobjects.Ubicacion;
import com.irentaspro.prop.domain.repository.PropiedadRepositorio;
import com.irentaspro.prop.infrastructure.persistence.entity.PropiedadEntity;

@Repository
public class PropiedadRepositorioImpl implements PropiedadRepositorio {

    private final JpaPropiedadRepositorio jpaRepo;

    public PropiedadRepositorioImpl(JpaPropiedadRepositorio jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Propiedad guardar(Propiedad propiedad) {
        PropiedadEntity entity = new PropiedadEntity();
        entity.setId(propiedad.getId());
        entity.setOwnerId(propiedad.getOwnerId());
        entity.setTitulo(propiedad.getTitulo());
        entity.setDescripcion(propiedad.getDescripcion());

        // Direccion
        entity.setCalle(propiedad.getDireccion().getCalle());
        entity.setDistrito(propiedad.getDireccion().getDistrito());
        entity.setProvincia(propiedad.getDireccion().getProvincia());

        // Ubicacion
        entity.setLatitud(propiedad.getUbicacion().getLatitud());
        entity.setLongitud(propiedad.getUbicacion().getLongitud());

        // Precio
        entity.setPrecio(propiedad.getPrecio().getValor());
        entity.setMoneda(propiedad.getPrecio().getMoneda());

        PropiedadEntity saved = jpaRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Propiedad> buscarPorId(UUID id) {
        return jpaRepo.findById(id).map(this::toDomain);
    }

    @Override
    public void eliminar(UUID id) {
        jpaRepo.deleteById(id);
    }

    @Override
    public List<Propiedad> findByOwnerId(UUID ownerId) {
        return jpaRepo.findByOwnerId(ownerId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Propiedad toDomain(PropiedadEntity e) {
        return new Propiedad(
                e.getOwnerId(),
                e.getTitulo(),
                e.getDescripcion(),
                new Direccion(e.getCalle(), e.getDistrito(), e.getProvincia()),
                new Ubicacion(e.getLatitud(), e.getLongitud(), e.getDistrito()),
                new Precio(e.getPrecio(), e.getMoneda()));
    }

}
