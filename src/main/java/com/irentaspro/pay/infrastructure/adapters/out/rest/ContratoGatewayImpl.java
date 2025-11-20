package com.irentaspro.pay.infrastructure.adapters.out.rest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.irentaspro.pay.domain.gateway.ContratoDTO;
import com.irentaspro.pay.domain.gateway.ContratoGateway;
import com.irentaspro.pay.domain.gateway.PagoRealizadoDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContratoGatewayImpl implements ContratoGateway {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://contratos/api/contratos";

    @Override
    public Optional<ContratoDTO> obtenerContrato(UUID contratoId) {

        ResponseEntity<com.irentaspro.ct.application.dto.ContratoDTO> res = restTemplate.getForEntity(
                BASE_URL + "/" + contratoId,
                com.irentaspro.ct.application.dto.ContratoDTO.class);

        var body = res.getBody();
        if (body == null)
            return Optional.empty();

        // Mapear al ContratoDTO del dominio de pago
        ContratoDTO dto = new ContratoDTO(
                body.getId(),
                body.getInquilinoId(),
                body.getMontoPendiente());

        return Optional.of(dto);
    }

    @Override
    public void registrarPagoEnContrato(UUID contratoId, PagoRealizadoDTO pago) {
        restTemplate.postForEntity(
                BASE_URL + "/" + contratoId + "/pagos",
                pago,
                Void.class);
    }
}
