package com.irentaspro.pay.infrastructure.adapters.out.rest;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.irentaspro.pay.domain.gateway.ContratoDTO;
import com.irentaspro.pay.domain.gateway.ContratoGateway;
import com.irentaspro.pay.domain.gateway.PagoRealizadoDTO;

import lombok.RequiredArgsConstructor;

/**
 * Gateway hacia microservicio contratos.
 */
@Component
@RequiredArgsConstructor
public class ContratoGatewayImpl implements ContratoGateway {

    private static final Logger log = LoggerFactory.getLogger(ContratoGatewayImpl.class);

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://contratos/api/contratos";

    @Override
    public Optional<ContratoDTO> obtenerContrato(UUID contratoId) {
        try {
            ResponseEntity<com.irentaspro.ct.application.dto.ContratoDTO> res = restTemplate.getForEntity(
                    BASE_URL + "/" + contratoId,
                    com.irentaspro.ct.application.dto.ContratoDTO.class);

            var body = res.getBody();
            if (body == null)
                return Optional.empty();

            ContratoDTO dto = new ContratoDTO(
                    body.getId(),
                    body.getInquilinoId(),
                    body.getMontoPendiente());

            return Optional.of(dto);
        } catch (Exception e) {
            log.error("[ContratoGateway] Error obteniendo contrato {}: {}", contratoId, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void registrarPagoEnContrato(UUID contratoId, PagoRealizadoDTO pago) {
        try {
            restTemplate.postForEntity(
                    BASE_URL + "/" + contratoId + "/pagos",
                    pago,
                    Void.class);
        } catch (Exception e) {
            log.error("[ContratoGateway] Error registrando pago en contrato {}: {}", contratoId, e.getMessage(), e);
            throw new IllegalStateException("No se pudo notificar al servicio de contratos", e);
        }
    }
}
