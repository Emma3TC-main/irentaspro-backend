package com.irentaspro.ct.application.mapper;

import java.util.stream.Collectors;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.ct.application.dto.ContratoDTO;
import com.irentaspro.ct.domain.model.Clausula;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.model.valueobjects.PeriodoContrato;

public class ContratoMapper {

    public static ContratoDTO toDTO(Contrato contrato) {
        var dto = new ContratoDTO();
        dto.setId(contrato.getId());
        dto.setPropiedadId(contrato.getPropiedadId());
        dto.setInquilinoId(contrato.getInquilinoId());
        dto.setInicio(contrato.getPeriodo().getInicio());
        dto.setFin(contrato.getPeriodo().getFin());
        dto.setMonto(contrato.getMonto().valor());
        dto.setMoneda(contrato.getMonto().moneda());
        dto.setEstado(contrato.getEstado().getEstado());
        dto.setClausulas(
                contrato.getClausulas().stream().map(Clausula::getDescripcion).collect(Collectors.toList()));
        return dto;
    }

    public static Contrato toDomain(ContratoDTO dto) {
        var periodo = new PeriodoContrato(dto.getInicio(), dto.getFin());
        var monto = new Monto(dto.getMonto(), dto.getMoneda());
        var clausulas = dto.getClausulas().stream()
                .map(c -> new Clausula("GENERAL", c))
                .collect(Collectors.toList());
        return new Contrato(dto.getPropiedadId(), dto.getInquilinoId(), periodo, monto, clausulas);
    }
}
