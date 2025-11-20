package com.irentaspro.ct.infrastructure.adapters.out.jpa.mapper;

import java.util.*;
import java.util.stream.Collectors;

import com.irentaspro.ct.domain.model.Clausula;
import com.irentaspro.ct.infrastructure.adapters.out.entities.ClausulaJpaEntity;
import com.irentaspro.ct.infrastructure.adapters.out.entities.ContratoJpaEntity;

public final class ClausulasUpdater {

    private ClausulasUpdater() {
    }

    public static void syncClausulas(
            ContratoJpaEntity entity,
            List<Clausula> clausulasDominio) {

        Map<String, ClausulaJpaEntity> actuales = entity.getClausulas().stream()
                .collect(Collectors.toMap(
                        c -> c.getTipo() + "|" + c.getDescripcion(),
                        c -> c));

        for (Clausula c : clausulasDominio) {

            String key = c.getTipo() + "|" + c.getDescripcion();

            ClausulaJpaEntity existente = actuales.get(key);

            if (existente == null) {
                // INSERT
                existente = new ClausulaJpaEntity();
                existente.setId(UUID.randomUUID());
                existente.setContrato(entity);
                entity.getClausulas().add(existente);
            }

            // UPDATE
            existente.setTipo(c.getTipo());
            existente.setDescripcion(c.getDescripcion());

            actuales.remove(key);
        }

        // DELETE restantes
        actuales.values().forEach(cl -> entity.getClausulas().remove(cl));
    }
}
