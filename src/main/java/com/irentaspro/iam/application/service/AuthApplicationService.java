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
        var policy = new PasswordPolicy();
        policy.validarComplejidad(password);

        var passwordHash = PasswordHash.crearDesdeTexto(password);

        var usuario = new Usuario(nombre, new Email(email), passwordHash);

        usuario.validarInvariantes();

        authRepositorio.guardar(usuario);
        return mapper.toDto(usuario);
    }

    public String autenticar(String email, String password) {
        //Buscar usuario por email
        var usuario = authRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        //Verificar password
        usuario.autenticar(password);

        // Crear token y sesión
        var authService = new AuthService(authRepositorio, new PasswordPolicy());
        String token = authService.issueToken(usuario);

        authService.registrarSesion(usuario, token); // Nueva función para registrar la sesión

        return token;
    }

}
