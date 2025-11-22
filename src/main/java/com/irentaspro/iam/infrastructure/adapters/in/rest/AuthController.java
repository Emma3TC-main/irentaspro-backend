package com.irentaspro.iam.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.irentaspro.iam.application.dto.UsuarioDTO;
import com.irentaspro.iam.application.service.AuthApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthApplicationService authApplicationService;

    /** Registro de usuario (form params) */
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password) {

        UsuarioDTO usuario = authApplicationService.registrarUsuario(nombre, email, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /** Autenticación (devuelve token como String plano) */
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password) {

        String token = authApplicationService.autenticar(email, password);
        return ResponseEntity.ok(token);
    }

    /** Usuario actual (JWT requerido) */
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> me(org.springframework.security.core.Authentication authentication) {
        // En JwtAuthenticationFilter dejas el "email" como principal
        String email = (String) authentication.getPrincipal();
        UsuarioDTO dto = authApplicationService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(dto);
    }
}
