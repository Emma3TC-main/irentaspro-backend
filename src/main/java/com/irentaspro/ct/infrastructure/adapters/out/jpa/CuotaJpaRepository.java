package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irentaspro.ct.infrastructure.adapters.out.entities.CuotaJpaEntity;

@Repository
public interface CuotaJpaRepository extends JpaRepository<CuotaJpaEntity, UUID> {

    List<CuotaJpaEntity> findByContratoId(UUID contratoId);

    List<CuotaJpaEntity> findByContratoIdAndPagadoFalse(UUID contratoId);
}
