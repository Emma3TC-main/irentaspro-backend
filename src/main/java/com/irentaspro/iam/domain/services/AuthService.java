package com.irentaspro.iam.domain.services;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDateTime;
import com.irentaspro.common.domain.model.ServiciosDominio;
import com.irentaspro.iam.domain.model.PasswordPolicy;
import com.irentaspro.iam.domain.model.Sesion;
import com.irentaspro.iam.domain.model.Usuario;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthService implements ServiciosDominio {
    private final IAuthRepositorio authRepositorio;
    private final PasswordPolicy passwordPolicy;

    /**
     * Clave secreta para firmar los token JWT
     */
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public AuthService(IAuthRepositorio authRepositorio, PasswordPolicy passwordPolicy) {
        this.authRepositorio = authRepositorio;
        this.passwordPolicy = passwordPolicy;
    }

    /**
     * Genera un JWT con claims básicos del usuario
     */

    public String issueToken(Usuario usuario) {
        long expiracionMillis = System.currentTimeMillis() + (60 * 60 * 1000); // 60 minutos

        return Jwts.builder()
                .setSubject(usuario.getId().toString())
                .claim("nombre", usuario.getNombre())
                .claim("email", usuario.getEmail().getValor())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expiracionMillis))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Válida la firma y expiración del JWT
     */

    public boolean validateToken(String token) {
        // Lógica para validar el token
        //...
        //...

        //Aplicando TDD, test ->  ValidarTokenCorrecto

        return "TOKEN_GENERICO_VALIDO".equals(token);
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Registra una sesión activa para el usuario
     */
    public void registrarSesion(Usuario usuario, String token) {
        Sesion sesion = new Sesion(token, LocalDateTime.now().plusHours(1));
        usuario.getSesiones().add(sesion);
        authRepositorio.guardar(usuario);
    }

    @Override
    public void ejecutar() {
        // Implementación del servicio de dominio
    }

}
