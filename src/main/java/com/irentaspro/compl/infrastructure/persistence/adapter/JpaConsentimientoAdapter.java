package com.irentaspro.compl.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.irentaspro.compl.domain.model.Consentimiento;
import com.irentaspro.compl.domain.repository.ConsentimientoRepository;
import com.irentaspro.compl.infrastructure.persistence.jpa.JpaConsentimientoRepository;
import com.irentaspro.compl.infrastructure.persistence.mapper.ComplMapper;

@Component
@RequiredArgsConstructor
public class JpaConsentimientoAdapter implements ConsentimientoRepository {

    private final JpaConsentimientoRepository jpa;

    @Override
    public Consentimiento guardar(Consentimiento consentimiento) {
        var entity = ComplMapper.toEntity(consentimiento);
        var saved = jpa.save(entity);
        return ComplMapper.toDomain(saved);
    }

    @Override
    public Optional<Consentimiento> buscarPorId(UUID id) {
        return jpa.findById(id).map(ComplMapper::toDomain);
    }

    @Override
    public Optional<Consentimiento> buscarPorUsuario(UUID usuarioId) {
        return jpa.findFirstByUsuarioIdOrderByFechaAceptacionDesc(usuarioId)
                .map(ComplMapper::toDomain);
    }
}
