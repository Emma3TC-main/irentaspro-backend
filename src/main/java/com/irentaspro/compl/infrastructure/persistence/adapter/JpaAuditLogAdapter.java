package com.irentaspro.compl.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.irentaspro.compl.domain.model.AuditLog;
import com.irentaspro.compl.domain.repository.AuditLogRepository;
import com.irentaspro.compl.infrastructure.persistence.jpa.JpaAuditLogRepository;
import com.irentaspro.compl.infrastructure.persistence.mapper.ComplMapper;

@Component
@RequiredArgsConstructor
public class JpaAuditLogAdapter implements AuditLogRepository {

    private final JpaAuditLogRepository jpa;

    @Override
    public AuditLog guardar(AuditLog log) {
        var entity = ComplMapper.toEntity(log);
        var saved = jpa.save(entity);
        return ComplMapper.toDomain(saved);
    }

    @Override
    public Optional<AuditLog> buscarPorId(UUID id) {
        return jpa.findById(id).map(ComplMapper::toDomain);
    }

    @Override
    public List<AuditLog> buscarPorUsuario(UUID usuarioId) {
        return jpa.findByUsuarioId(usuarioId).stream().map(ComplMapper::toDomain).collect(Collectors.toList());
    }
}
