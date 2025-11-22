package com.irentaspro.compl.infrastructure.persistence.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.irentaspro.compl.infrastructure.persistence.entity.AuditLogEntity;

public interface JpaAuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {
    List<AuditLogEntity> findByUsuarioId(UUID usuarioId);
}
