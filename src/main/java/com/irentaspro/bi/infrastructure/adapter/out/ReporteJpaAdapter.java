package com.irentaspro.bi.infrastructure.adapter.out;

import com.irentaspro.bi.domain.model.KPIContrato;
import com.irentaspro.bi.domain.model.KPIGlobal;
import com.irentaspro.bi.domain.port.ReporteRepository;
import com.irentaspro.ct.infrastructure.adapters.out.entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class ReporteJpaAdapter implements ReporteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<KPIContrato> obtenerKPIContratos() {
        var query = em.createQuery("""
                SELECT new com.irentaspro.reportes.domain.model.KPIContrato(
                c.id,
                c.monto,
                c.montoPendiente,
                c.inicio,
                c.fin,
                c.estado
                )
                FROM ContratoJpaEntity c
                """, KPIContrato.class);

        return query.getResultList();
    }

    @Override
    public KPIGlobal obtenerKPIGeneral() {

        long totalContratos = em.createQuery("SELECT COUNT(c) FROM ContratoJpaEntity c", Long.class).getSingleResult();
        long contratosActivos = em
                .createQuery("SELECT COUNT(c) FROM ContratoJpaEntity c WHERE c.estado = 'ACTIVO'", Long.class)
                .getSingleResult();

        BigDecimal ingresosTotales = Optional.ofNullable(
                em.createQuery("SELECT SUM(p.monto) FROM PagoEntity p", BigDecimal.class)
                        .getSingleResult())
                .orElse(BigDecimal.ZERO);

        long pagosRealizados = em
                .createQuery("SELECT COUNT(p) FROM PagoEntity p WHERE p.estado = 'COMPLETADO'", Long.class)
                .getSingleResult();

        BigDecimal deudaPendiente = Optional.ofNullable(
                em.createQuery("SELECT SUM(c.montoPendiente) FROM ContratoJpaEntity c", BigDecimal.class)
                        .getSingleResult())
                .orElse(BigDecimal.ZERO);

        return new KPIGlobal(
                totalContratos,
                contratosActivos,
                ingresosTotales,
                pagosRealizados,
                deudaPendiente,
                LocalDate.now());
    }
}