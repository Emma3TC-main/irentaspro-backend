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
        // --- Limpieza y validación del correo ---
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }

        // Normalizar (trim + lowercase)
        email = email.trim().toLowerCase();

        // Validar formato con expresión robusta

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }

        // Evitar comas, espacios o caracteres extraños
        if (email.contains(",") || email.contains(" ")) {
            throw new IllegalArgumentException("El correo electrónico contiene caracteres inválidos");
        }

        // --- Verificar duplicado ---
        authRepositorio.buscarPorEmail(email).ifPresent(u -> {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        });

        // --- Validación de la contraseña ---
        var policy = new PasswordPolicy();
        policy.validarComplejidad(password);

        // --- Crear hash y entidad de dominio ---
        var passwordHash = PasswordHash.crearDesdeTexto(password);
        var usuario = new Usuario(nombre, new Email(email), passwordHash);

        usuario.validarInvariantes();

        // --- Guardar en el repositorio ---
        authRepositorio.guardar(usuario);

        // --- Devolver DTO ---
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

}
