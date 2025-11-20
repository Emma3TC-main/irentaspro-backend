package com.irentaspro.ct.infrastructure.adapters.out.jpa.mapper;

import com.irentaspro.common.domain.model.valueobjects.HashDocumento;
import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.domain.model.*;
import com.irentaspro.ct.domain.model.valueobjects.*;
import com.irentaspro.ct.infrastructure.adapters.out.entities.*;
import com.irentaspro.ct.infrastructure.adapters.out.jpa.FirmaDigitalEmbeddable;

public final class ContratoJpaMapper {

    private ContratoJpaMapper() {
    }

    public static void mergeIntoEntity(Contrato dominio, ContratoJpaEntity entity) {

        entity.setId(dominio.getId());
        entity.setPropiedadId(dominio.getPropiedadId());
        entity.setPropietarioId(dominio.getPropietarioId());
        entity.setInquilinoId(dominio.getInquilinoId());

        // Periodo
        entity.setInicio(dominio.getPeriodo().getInicio());
        entity.setFin(dominio.getPeriodo().getFin());

        // Monto
        entity.setMonto(dominio.getMonto().valor());
        entity.setMoneda(dominio.getMonto().moneda());

        // Estado / Monto pendiente
        entity.setEstado(dominio.getEstado().name());
        entity.setMontoPendiente(dominio.getMontoPendiente());

        // Firma digital
        if (dominio.getFirmaDigital() != null) {
            var f = dominio.getFirmaDigital();
            entity.setFirma(new FirmaDigitalEmbeddable(
                    f.getProveedor(),
                    f.getCertificado(),
                    f.getHashDocumento() != null ? f.getHashDocumento().valor() : null,
                    f.getHashDocumento() != null ? f.getHashDocumento().algoritmo() : null,
                    f.getSelloTiempo()));
        } else {
            entity.setFirma(null);
        }

        // MERGE inteligente
        ClausulasUpdater.syncClausulas(entity, dominio.getClausulas());
        CuotasUpdater.syncCuotas(entity, dominio.getCalendario());
    }

    // ===========================================================
    // JPA → DOMINIO (lo tuyo, limpio)
    // ===========================================================
    public static Contrato toDomain(ContratoJpaEntity e) {
        if (e == null)
            return null;

        var periodo = new PeriodoContrato(e.getInicio(), e.getFin());
        var monto = new Monto(e.getMonto(), e.getMoneda());

        var clausulas = e.getClausulas().stream()
                .map(c -> new Clausula(c.getTipo(), c.getDescripcion()))
                .toList();

        var contrato = new Contrato(
                e.getPropiedadId(),
                e.getPropietarioId(),
                e.getInquilinoId(),
                periodo,
                monto,
                clausulas);

        contrato.restaurarId(e.getId());
        contrato.restaurarMontoPendiente(e.getMontoPendiente());
        contrato.restaurarEstado(EstadoContrato.valueOf(e.getEstado()));

        if (!e.getCuotas().isEmpty()) {
            contrato.restaurarCalendario(
                    e.getCuotas().stream()
                            .map(q -> {
                                var c = new CuotaContrato(q.getNumero(), q.getFecha(), q.getMonto());
                                if (q.isPagado())
                                    c.marcarComoPagada();
                                return c;
                            })
                            .toList());
        }

        if (e.getFirma() != null) {
            var fe = e.getFirma();
            HashDocumento hash = null;
            if (fe.getHashValor() != null && fe.getHashAlgoritmo() != null)
                hash = new HashDocumento(fe.getHashValor(), fe.getHashAlgoritmo());

            contrato.restaurarFirma(
                    new FirmaDigital(
                            fe.getProveedor(),
                            fe.getCertificado(),
                            hash,
                            fe.getSelloTiempo()));
        }

        return contrato;
    }
}
