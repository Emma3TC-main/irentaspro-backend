package com.irentaspro.pay.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.application.mapper.PagoMapper;
import com.irentaspro.pay.domain.model.Pago;
import com.irentaspro.pay.domain.repository.PagoRepositorio;
import com.irentaspro.pay.domain.services.PagoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PagoApplicationService {

    private final PagoRepositorio pagoRepositorio;
    private final PagoService pagoService;

    /**
     * Registra un nuevo pago en el sistema.
     */
    public PagoDTO registrarPago(PagoDTO dto) {
        Pago pago = pagoService.iniciarPago(
                dto.getContratoId(),
                dto.getUsuarioId(),
                new Monto(dto.getMonto(), dto.getMoneda()),
                dto.getMetodo(),
                dto.getTipoPago());

        pagoRepositorio.guardar(pago);
        return PagoMapper.toDTO(pago);
    }

    /**
     * Confirma un pago existente por su ID.
     */
    public void confirmarPago(UUID id) {
        Pago pago = pagoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        pagoService.confirmar(pago);
        pagoRepositorio.guardar(pago);
    }

    /**
     * Obtiene un pago por su ID.
     */
    @Transactional(readOnly = true)
    public PagoDTO obtenerPago(UUID id) {
        Pago pago = pagoRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));

        return PagoMapper.toDTO(pago);
    }
}
