package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irentaspro.ct.infrastructure.adapters.out.entities.ContratoJpaEntity;

@Repository
public interface ContratoJpaRepository extends JpaRepository<ContratoJpaEntity, UUID> {
}
