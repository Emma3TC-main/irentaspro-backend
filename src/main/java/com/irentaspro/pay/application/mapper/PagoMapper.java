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
                .estado(pago.getEstado())
                .referenciaExterna(pago.getReferenciaExterna())
                .comprobanteFiscal(
                        pago.getComprobanteFiscal() != null
                                ? ComprobanteFiscalDTO.builder()
                                        .tipo(pago.getComprobanteFiscal().getTipo())
                                        .xml(pago.getComprobanteFiscal().getXml())
                                        .ticketSUNAT(pago.getComprobanteFiscal().getTicketSUNAT())
                                        .build()
                                : null)
                .build();
    }

    public static Pago toDomain(PagoDTO dto) {
        if (dto == null)
            return null;

        Pago pago = new Pago(
                dto.getContratoId(),
                dto.getUsuarioId(),
                new Monto(dto.getMonto(), dto.getMoneda()),
                dto.getMetodo(),
                dto.getTipoPago(),
                dto.getEstado());

        pago.asignarReferenciaExterna(dto.getReferenciaExterna());

        if (dto.getComprobanteFiscal() != null) {
            pago.generarComprobante(
                    new ComprobanteFiscal(
                            dto.getComprobanteFiscal().getTipo(),
                            dto.getComprobanteFiscal().getXml(),
                            dto.getComprobanteFiscal().getTicketSUNAT()));
        }

        return pago;
    }
}
