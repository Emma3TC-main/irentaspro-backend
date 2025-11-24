package com.irentaspro.pay.infrastructure.adapters.out.gateway;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.irentaspro.pay.domain.gateway.PasarelaPagoGateway;

@Component
public class PaypalGatewayAdapter implements PasarelaPagoGateway {

    private static final Logger log = LoggerFactory.getLogger(PaypalGatewayAdapter.class);

    private static final String PAYPAL_API_URL = "https://api.sandbox.paypal.com";

    private final String clientId;
    private final String clientSecret;
    private final RestTemplate restTemplate = new RestTemplate();

    public PaypalGatewayAdapter(
            @Value("${spring.paypal.client-id}") String clientId,
            @Value("${spring.paypal.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String procesarPago(BigDecimal monto, String moneda, String metodo, String referencia) {
        try {
            String accessToken = obtenerTokenDeAcceso();
            String url = PAYPAL_API_URL + "/v2/checkout/orders";

            Map<String, Object> body = Map.of(
                    "intent", "CAPTURE",
                    "purchase_units", List.of(Map.of(
                            "amount", Map.of(
                                    "currency_code", moneda,
                                    "value", monto.toString()),
                            "reference_id", referencia)),
                    "application_context", Map.of(
                            "return_url", "http://localhost:4200/premium/confirmar",
                            "cancel_url", "http://localhost:4200/premium/cancelado"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("id");
            } else {
                throw new IllegalStateException("Error al crear orden PayPal");
            }

        } catch (Exception e) {
            log.error("Error procesando pago PayPal: {}", e.getMessage(), e);
            throw new IllegalStateException("Error procesando pago PayPal", e);
        }
    }

    public boolean capturarOrden(String orderId) {
        try {
            String url = PAYPAL_API_URL + "/v2/checkout/orders/" + orderId + "/capture";
            String token = obtenerTokenDeAcceso();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("Error capturando orden PayPal: {}", e.getMessage(), e);
            return false;
        }
    }

    private String obtenerTokenDeAcceso() {
        try {
            String url = PAYPAL_API_URL + "/v1/oauth2/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(clientId, clientSecret);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("access_token");
            }
            throw new IllegalStateException("No se pudo obtener token de PayPal");
        } catch (Exception e) {
            log.error("Error obteniendo token PayPal: {}", e.getMessage(), e);
            throw new IllegalStateException("Error obteniendo token PayPal", e);
        }
    }
}
