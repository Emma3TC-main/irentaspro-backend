package com.irentaspro.ct.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.application.dto.ContratoDTO;
import com.irentaspro.ct.application.dto.CuotaContratoDTO;
import com.irentaspro.ct.domain.model.Clausula;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.valueobjects.CuotaContrato;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

public final class ContratoApplicationMapper {

    private ContratoApplicationMapper() {
    }

    public static ContratoDTO toDTO(Contrato contrato) {
        if (contrato == null)
            return null;

        return ContratoDTO.builder()
                .id(contrato.getId())
                .propiedadId(contrato.getPropiedadId())
                .propietarioId(contrato.getPropietarioId()) // <--- FALTABA
                .inquilinoId(contrato.getInquilinoId())
                .inicio(contrato.getPeriodo().getInicio())
                .fin(contrato.getPeriodo().getFin())
                .monto(contrato.getMonto().valor())
                .moneda(contrato.getMonto().moneda())
                .estado(contrato.getEstado().toString())
                .clausulas(contrato.getClausulas().stream()
                        .map(Clausula::getDescripcion)
                        .collect(Collectors.toList()))
                .montoPendiente(contrato.getMontoPendiente())
                .build();
    }

    public static Contrato toDomain(ContratoDTO dto) {
        if (dto == null)
            return null;
        PeriodoContrato periodo = new PeriodoContrato(dto.getInicio(), dto.getFin());
        Monto monto = new Monto(dto.getMonto(), dto.getMoneda());
        List<Clausula> clausulas = dto.getClausulas() == null ? List.of()
                : dto.getClausulas().stream().map(c -> new Clausula("GENERAL", c)).collect(Collectors.toList());

        return new Contrato(dto.getPropiedadId(), dto.getPropietarioId(), dto.getInquilinoId(), periodo, monto,
                clausulas);
    }

    public static CuotaContratoDTO toCuotaDTO(CuotaContrato cuota) {
        if (cuota == null)
            return null;
        return new CuotaContratoDTO(cuota.getNumero(), cuota.getFechaPago(), cuota.getMonto(), cuota.isPagado());
    }

    public static java.util.List<CuotaContratoDTO> toCuotasDTOList(java.util.List<CuotaContrato> cuotas) {
        return cuotas.stream().map(ContratoApplicationMapper::toCuotaDTO).collect(Collectors.toList());
    }
}
