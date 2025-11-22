package com.irentaspro.compl.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.irentaspro.compl.domain.model.SolicitudARCO;
import com.irentaspro.compl.domain.repository.SolicitudARCORepository;
import com.irentaspro.compl.infrastructure.persistence.jpa.JpaSolicitudARCORepository;
import com.irentaspro.compl.infrastructure.persistence.mapper.ComplMapper;

@Component
@RequiredArgsConstructor
public class SolicitudARCOAdapter implements SolicitudARCORepository {
    private final JpaSolicitudARCORepository jpa;

    @Override
    public SolicitudARCO guardar(SolicitudARCO solicitud) {
        var entity = ComplMapper.toEntity(solicitud);
        var saved = jpa.save(entity);
        return ComplMapper.toDomain(saved);
    }

    @Override
    public Optional<SolicitudARCO> buscarPorId(UUID id) {
        return jpa.findById(id).map(ComplMapper::toDomain);
    }

    @Override
    public List<SolicitudARCO> buscarPorUsuario(UUID usuarioId) {
        return jpa.findByUsuarioId(usuarioId).stream().map(ComplMapper::toDomain).collect(Collectors.toList());
    }

    @Override
public List<SolicitudARCO> buscarTodos() {
    return jpa.findAll()
            .stream()
            .map(ComplMapper::toDomain)
            .collect(Collectors.toList());
}

}
