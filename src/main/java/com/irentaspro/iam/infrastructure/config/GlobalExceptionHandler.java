package com.irentaspro.iam.infrastructure.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja errores de validación de negocio o argumentos inválidos
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String msg = ex.getMessage().toLowerCase();
        if (msg.contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else if (msg.contains("contraseña") || msg.contains("password")) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (msg.contains("registrado")) {
            status = HttpStatus.CONFLICT; // conflicto: recurso duplicado
        }

        return buildErrorResponse(status, ex.getMessage());
    }

    /**
     * Cualquier otro error inesperado
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    /**
     * Construye el cuerpo de la respuesta de error
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
