package com.irentaspro.pay.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irentaspro.pay.infrastructure.entity.TransaccionPSPEntity;

@Repository
public interface JpaTransaccionPSPRepository extends JpaRepository<TransaccionPSPEntity, UUID> {
    Optional<TransaccionPSPEntity> findByRef(String ref);
}
