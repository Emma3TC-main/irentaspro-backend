package com.irentaspro.ct.infrastructure.adapters.out.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.Clausula;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;
import com.irentaspro.ct.domain.repository.ContratoRepositorio;
import com.irentaspro.ct.infrastructure.entities.ContratoEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContratoRepositorioAdapter implements ContratoRepositorio {

        private final ContratoJpaRepository jpa;

        @Override
        public Contrato guardar(Contrato contrato) {
                var entity = new ContratoEntity();
                entity.setId(contrato.getId());
                entity.setPropiedadId(contrato.getPropiedadId());
                entity.setInquilinoId(contrato.getInquilinoId());
                entity.setInicio(contrato.getPeriodo().getInicio());
                entity.setFin(contrato.getPeriodo().getFin());
                entity.setMonto(contrato.getMonto().valor().doubleValue());
                entity.setMoneda(contrato.getMonto().moneda());
                entity.setEstado(contrato.getEstado().getEstado());
                entity.setClausulas(
                                contrato.getClausulas().stream()
                                                .map(Clausula::getDescripcion)
                                                .toList());

                var saved = jpa.save(entity);

                return mapToDomain(saved);
        }

        @Override
        public Optional<Contrato> buscarPorId(UUID id) {
                return jpa.findById(id).map(this::mapToDomain);
        }

        @Override
        public List<Contrato> buscarTodos() {
                return jpa.findAll().stream()
                                .map(this::mapToDomain)
                                .toList();
        }

        @Override
        public void eliminar(UUID id) {
                jpa.deleteById(id);
        }

        /**
         * Convierte una entidad persistida en un agregado de dominio Contrato.
         */
        private Contrato mapToDomain(ContratoEntity e) {
                var periodo = new PeriodoContrato(e.getInicio(), e.getFin());
                var monto = new Monto(
                                BigDecimal.valueOf(e.getMonto()),
                                e.getMoneda());
                var clausulas = e.getClausulas().stream()
                                .map(c -> new Clausula("GENERAL", c))
                                .toList();

                return new Contrato(
                                e.getPropiedadId(),
                                e.getInquilinoId(),
                                periodo,
                                monto,
                                clausulas);
        }
}
