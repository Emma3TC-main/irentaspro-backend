package com.irentaspro.pay.infrastructure.adapters.out.gateway;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.irentaspro.pay.domain.gateway.PasarelaPagoGateway;

@Component
public class PaypalGatewayAdapter implements PasarelaPagoGateway {

    private static final String PAYPAL_API_URL = "https://api.sandbox.paypal.com";
    private static final String CLIENT_ID = "TU_CLIENT_ID_SANDBOX";
    private static final String CLIENT_SECRET = "TU_CLIENT_SECRET_SANDBOX";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String procesarPago(BigDecimal monto, String moneda, String metodo, String referencia) {
        try {
            String accessToken = obtenerTokenDeAcceso();

            String url = PAYPAL_API_URL + "/v2/checkout/orders";

            Map<String, Object> body = Map.of(
                    "intent", "CAPTURE",
                    "purchase_units", List.of(Map.of(
                            "amount", Map.of("currency_code", moneda, "value", monto.toString()))));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("id"); // ID del pago de PayPal
            } else {
                throw new IllegalStateException("Error al procesar pago en PayPal: respuesta inválida");
            }

        } catch (HttpClientErrorException e) {
            throw new IllegalStateException("Error HTTP PayPal: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new IllegalStateException("Error inesperado al procesar pago con PayPal: " + e.getMessage(), e);
        }
    }

    private String obtenerTokenDeAcceso() {
        String url = PAYPAL_API_URL + "/v1/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }
        throw new IllegalStateException("No se pudo obtener token de acceso de PayPal");
    }
}
