package com.irentaspro.prop.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irentaspro.prop.infrastructure.persistence.entity.PropiedadEntity;

public interface JpaPropiedadRepositorio extends JpaRepository<PropiedadEntity, UUID> {
    List<PropiedadEntity> findByOwnerId(UUID ownerId);
}