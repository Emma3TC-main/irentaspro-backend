package com.irentaspro.pay.infrastructure.adapters.in;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Wrapper simple para respuestas con un solo mensaje.
 */
@Data
@AllArgsConstructor
public class ApiSimpleResponse {
    private String mensaje;
}
