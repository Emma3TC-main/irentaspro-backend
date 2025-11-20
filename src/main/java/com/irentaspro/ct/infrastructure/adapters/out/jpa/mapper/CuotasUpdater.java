package com.irentaspro.ct.infrastructure.adapters.out.jpa.mapper;

import java.util.*;
import java.util.stream.Collectors;

import com.irentaspro.ct.domain.model.valueobjects.CuotaContrato;
import com.irentaspro.ct.infrastructure.adapters.out.entities.CuotaJpaEntity;
import com.irentaspro.ct.infrastructure.adapters.out.entities.ContratoJpaEntity;

public final class CuotasUpdater {

    private CuotasUpdater() {}

    public static void syncCuotas(
            ContratoJpaEntity entity,
            List<CuotaContrato> cuotasDominio) {

        Map<Integer, CuotaJpaEntity> actuales = entity.getCuotas().stream()
                .collect(Collectors.toMap(
                        CuotaJpaEntity::getNumero,
                        c -> c
                ));

        for (CuotaContrato c : cuotasDominio) {

            CuotaJpaEntity existente = actuales.get(c.getNumero());

            if (existente == null) {
                // INSERT
                existente = new CuotaJpaEntity();
                existente.setId(UUID.randomUUID());
                existente.setContrato(entity);
                entity.getCuotas().add(existente);
            }

            // UPDATE
            existente.setNumero(c.getNumero());
            existente.setFecha(c.getFechaPago());
            existente.setMonto(c.getMonto());
            existente.setPagado(c.isPagado());

            actuales.remove(c.getNumero());
        }

        // DELETE
        actuales.values().forEach(c -> entity.getCuotas().remove(c));
    }
}
