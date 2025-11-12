package com.irentaspro.ct.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.ct.application.dto.ContratoDTO;
import com.irentaspro.ct.application.mapper.ContratoMapper;
import com.irentaspro.ct.domain.model.Contrato;
import com.irentaspro.ct.domain.repository.ContratoRepositorio;
import com.irentaspro.ct.domain.services.ContratoService;

import lombok.RequiredArgsConstructor;

/**
 * Capa de aplicación: coordina las operaciones entre dominio y repositorio.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ContratoApplicationService {
    private final ContratoRepositorio contratoRepositorio;
    private final ContratoService contratoService;

    /**
     * Crea un nuevo contrato.
     */
    public ContratoDTO crearContrato(ContratoDTO dto) {
        Contrato contrato = ContratoMapper.toDomain(dto);
        contrato.validarInvariantes();
        Contrato guardado = contratoRepositorio.guardar(contrato);
        return ContratoMapper.toDTO(guardado);
    }

    /**
     * Firma digitalmente un contrato existente.
     */
    public ContratoDTO firmarContrato(UUID contratoId) {
        Contrato contrato = contratoRepositorio.buscarPorId(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));
        contratoService.firmarContrato(contrato);
        contratoRepositorio.guardar(contrato); // persiste cambios
        return ContratoMapper.toDTO(contrato);
    }

    /**
     * Lista todos los contratos existentes.
     */
    public List<ContratoDTO> listarContratos() {
        return contratoRepositorio.buscarTodos()
                .stream()
                .map(ContratoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
