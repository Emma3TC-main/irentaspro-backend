package com.irentaspro.compl.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.irentaspro.compl.infrastructure.persistence.entity.ConsentimientoEntity;

public interface JpaConsentimientoRepository extends JpaRepository<ConsentimientoEntity, UUID> {
    Optional<ConsentimientoEntity> findFirstByUsuario_IdOrderByFechaAceptacionDesc(UUID usuarioId);
}
