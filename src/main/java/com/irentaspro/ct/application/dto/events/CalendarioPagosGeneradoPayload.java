package com.irentaspro.ct.application.dto.events;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.irentaspro.ct.domain.model.valueobjects.CuotaContrato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarioPagosGeneradoPayload implements Serializable {
    private UUID contratoId;
    private List<CuotaSimple> cuotas;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CuotaSimple {
        private LocalDate fecha;
        private BigDecimal monto;
    }

    public static CalendarioPagosGeneradoPayload from(UUID contratoId, List<CuotaContrato> cuotas) {
        var list = cuotas.stream()
                .map(c -> new CuotaSimple(c.getFechaPago(), c.getMonto()))
                .collect(Collectors.toList());
        return new CalendarioPagosGeneradoPayload(contratoId, list);
    }
}
