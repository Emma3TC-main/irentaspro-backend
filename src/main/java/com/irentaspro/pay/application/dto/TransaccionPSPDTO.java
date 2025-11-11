package com.irentaspro.pay.application.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionPSPDTO {
    private String provider;
    private String ref;
    private Map<String, Object> payload; // Datos crudos del proveedor de pagos (PSP)
}
