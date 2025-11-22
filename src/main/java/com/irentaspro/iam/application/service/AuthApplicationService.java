package com.irentaspro.iam.application.service;

import org.springframework.stereotype.Service;

import com.irentaspro.iam.application.dto.UsuarioDTO;
import com.irentaspro.iam.application.mapper.UsuarioMapper;
import com.irentaspro.iam.domain.model.PasswordPolicy;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import com.irentaspro.iam.domain.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final IAuthRepositorio authRepositorio;
    private final UsuarioMapper mapper;

    public UsuarioDTO registrarUsuario(String nombre, String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }
        email = email.trim().toLowerCase();

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
        if (email.contains(",") || email.contains(" ")) {
            throw new IllegalArgumentException("El correo electrónico contiene caracteres inválidos");
        }

        authRepositorio.buscarPorEmail(email).ifPresent(u -> {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        });

        var policy = new PasswordPolicy();
        policy.validarComplejidad(password);

        var passwordHash = PasswordHash.crearDesdeTexto(password);
        var usuario = new Usuario(nombre, new Email(email), passwordHash);
        usuario.validarInvariantes();
        usuario.setTipoCuenta("FREE");

        authRepositorio.guardar(usuario);

        return mapper.toDto(usuario);
    }

    public String autenticar(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Debe ingresar un correo electrónico válido");
        }
        email = email.trim().toLowerCase();

        var usuario = authRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.autenticar(password);

        var authService = new AuthService(authRepositorio, new PasswordPolicy());
        return authService.issueToken(usuario);
    }

    /** NUEVO: obtener DTO del usuario por email (para /me) */
    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email inválido");
        }
        var usuario = authRepositorio.buscarPorEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toDto(usuario);
    }
}
