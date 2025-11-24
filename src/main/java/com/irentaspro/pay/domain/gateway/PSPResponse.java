package com.irentaspro.pay.domain.gateway;

public record PSPResponse(
        String ref,
        String status,
        String method,
        String type,
        String currency,
        String ticketSUNAT,
        Double amount) {
}
