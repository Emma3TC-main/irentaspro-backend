package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irentaspro.ct.infrastructure.entities.ContratoEntity;

import java.util.UUID;

public interface ContratoJpaRepository extends JpaRepository<ContratoEntity, UUID> {

}