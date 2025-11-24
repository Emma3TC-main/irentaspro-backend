package com.irentaspro.pay.application.mapper;

import com.irentaspro.common.domain.model.valueobjects.Monto;
import com.irentaspro.pay.application.dto.ComprobanteFiscalDTO;
import com.irentaspro.pay.application.dto.PagoDTO;
import com.irentaspro.pay.domain.model.ComprobanteFiscal;
import com.irentaspro.pay.domain.model.Pago;

public class PagoMapper {

    public static PagoDTO toDTO(Pago pago) {
        if (pago == null)
            return null;
        return PagoDTO.builder()
                .id(pago.getId())
                .contratoId(pago.getContratoId())
                .usuarioId(pago.getUsuarioId())
                .monto(pago.getMonto().valor())
                .moneda(pago.getMonto().moneda())
                .metodo(pago.getMetodo())
                .tipoPago(pago.getTipoPago())
                .estado(pago.getEstado().name())
                .referenciaExterna(pago.getReferenciaExterna())
                .comprobanteFiscal(pago.getComprobanteFiscal() != null
                        ? new ComprobanteFiscalDTO(
                                pago.getComprobanteFiscal().getTipo(),
                                pago.getComprobanteFiscal().getXml(),
                                pago.getComprobanteFiscal().getTicketSUNAT())
                        : null)
                .build();
    }
}
