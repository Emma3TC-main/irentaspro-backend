package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.repository.ContratoRepositorio;
import com.irentaspro.ct.infrastructure.adapters.out.entities.ContratoJpaEntity;
import com.irentaspro.ct.infrastructure.adapters.out.jpa.mapper.ContratoJpaMapper;

@Repository
@RequiredArgsConstructor
public class ContratoRepositorioAdapter implements ContratoRepositorio {

        private final ContratoJpaRepository jpa;

        @Override
        public Contrato guardar(Contrato dominio) {

                ContratoJpaEntity entity = dominio.getId() == null
                                ? new ContratoJpaEntity()
                                : jpa.findById(dominio.getId()).orElse(new ContratoJpaEntity());

                ContratoJpaMapper.mergeIntoEntity(dominio, entity);

                var saved = jpa.save(entity);

                return ContratoJpaMapper.toDomain(saved);
        }

        @Override
        public Optional<Contrato> buscarPorId(UUID id) {
                return jpa.findById(id).map(ContratoJpaMapper::toDomain);
        }

        @Override
        public List<Contrato> buscarTodos() {
                return jpa.findAll().stream()
                                .map(ContratoJpaMapper::toDomain)
                                .toList();
        }

        @Override
        public void eliminar(UUID id) {
                jpa.deleteById(id);
        }
}
