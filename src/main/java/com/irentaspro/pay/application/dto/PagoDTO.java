package com.irentaspro.pay.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private UUID id;
    private UUID contratoId;
    private UUID usuarioId;
    private BigDecimal monto;
    private String moneda;
    private String metodo;
    private String tipoPago;
    private String estado;
    private String referenciaExterna;
    private ComprobanteFiscalDTO comprobanteFiscal;
}
