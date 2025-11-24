package com.irentaspro.iam.application.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.irentaspro.iam.application.dto.UsuarioDTO;
import com.irentaspro.iam.application.mapper.UsuarioMapper;
import com.irentaspro.iam.domain.model.PasswordPolicy;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.model.valueobject.Email;
import com.irentaspro.iam.domain.model.valueobject.PasswordHash;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import com.irentaspro.iam.domain.services.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final IAuthRepositorio authRepositorio;
    private final UsuarioMapper mapper;

    // Reutilizamos una sola política (no necesitas recrearla cada vez)
    private final PasswordPolicy passwordPolicy = new PasswordPolicy();

    // Servicio de autenticación centralizado
    private AuthService authService(IAuthRepositorio repo) {
        return new AuthService(repo, passwordPolicy);
    }

    // =========================================================
    // REGISTRAR
    // =========================================================
    public UsuarioDTO registrarUsuario(String nombre, String email, String password) {

        String emailNormalizado = normalizarEmail(email);
        validarEmailDisponible(emailNormalizado);

        passwordPolicy.validarComplejidad(password);

        PasswordHash passwordHash = PasswordHash.crearDesdeTexto(password);

        Usuario usuario = new Usuario(nombre, new Email(emailNormalizado), passwordHash);
        usuario.setTipoCuenta("FREE");
        usuario.validarInvariantes();

        authRepositorio.guardar(usuario);

        return mapper.toDto(usuario);
    }

    // =========================================================
    // AUTENTICAR
    // =========================================================
    public String autenticar(String email, String password) {

        String emailNormalizado = normalizarEmail(email);

        Usuario usuario = authRepositorio.buscarPorEmail(emailNormalizado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.autenticar(password);

        return authService(authRepositorio).issueToken(usuario);
    }

    // =========================================================
    // OBTENER USUARIO (para /me)
    // =========================================================
    public UsuarioDTO obtenerUsuarioPorEmail(String email) {

        String emailNormalizado = normalizarEmail(email);

        Usuario usuario = authRepositorio.buscarPorEmail(emailNormalizado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return mapper.toDto(usuario);
    }

    // =========================================================
    // UPGRADE DE CUENTA
    // =========================================================
    public void upgradeCuenta(String email) {

        String emailNormalizado = normalizarEmail(email);

        Usuario usuario = authRepositorio.buscarPorEmail(emailNormalizado)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setTipoCuenta("PREMIUM");
        usuario.setFechaVencimiento(LocalDate.now().plusYears(1));

        authRepositorio.guardar(usuario);
    }

    @Transactional
    public void upgradeCuenta(UUID usuarioId) {
        Usuario usuario = authRepositorio.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setTipoCuenta("PREMIUM");
        usuario.setFechaVencimiento(LocalDate.now().plusYears(1));
        authRepositorio.guardar(usuario);
    }

    // =========================================================
    // MÉTODOS PRIVADOS UTILITARIOS
    // =========================================================

    private String normalizarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Debe proporcionar un correo electrónico válido");
        }
        String normalized = email.trim().toLowerCase();

        if (!normalized.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
        if (normalized.contains(",") || normalized.contains(" ")) {
            throw new IllegalArgumentException("El correo electrónico contiene caracteres inválidos");
        }

        return normalized;
    }

    private void validarEmailDisponible(String email) {
        authRepositorio.buscarPorEmail(email).ifPresent(u -> {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        });
    }
}
