package com.irentaspro.pay.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteFiscalDTO {
    private String tipo;
    private String xml;
    private String ticketSUNAT;
}
