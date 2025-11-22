package com.irentaspro.compl.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.irentaspro.compl.domain.model.*;

public interface AuditLogRepository {
    AuditLog guardar(AuditLog log);

    Optional<AuditLog> buscarPorId(UUID id);

    List<AuditLog> buscarPorUsuario(UUID usuarioId);
}

