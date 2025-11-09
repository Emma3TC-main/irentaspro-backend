package com.irentaspro.iam.infrastructure.adapters.in.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.irentaspro.iam.application.dto.UsuarioDTO;
import com.irentaspro.iam.application.service.AuthApplicationService;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthApplicationService authApplicationService;

    @PostMapping("/register")
    public UsuarioDTO register(@RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password) {
        return authApplicationService.registrarUsuario(nombre, email, password);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password) {
        return authApplicationService.autenticar(email, password);
    }
}