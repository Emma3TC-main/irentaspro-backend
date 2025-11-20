package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irentaspro.ct.infrastructure.adapters.out.entities.ClausulaJpaEntity;

@Repository
public interface ClausulaJpaRepository extends JpaRepository<ClausulaJpaEntity, UUID> {

    // para listar cláusulas de un contrato
    java.util.List<ClausulaJpaEntity> findByContratoId(UUID contratoId);
}
