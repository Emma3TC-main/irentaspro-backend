package com.irentaspro.iam.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.irentaspro.iam.domain.model.PasswordPolicy;
import com.irentaspro.iam.domain.repository.IAuthRepositorio;
import com.irentaspro.iam.domain.services.AuthService;

@Configuration
public class AuthConfig {
    @Bean
    public AuthService authService(IAuthRepositorio authRepositorio) {
        // Si PasswordPolicy es una clase simple, la puedes construir aquí directamente
        PasswordPolicy passwordPolicy = new PasswordPolicy();
        return new AuthService(authRepositorio, passwordPolicy);
    }
}
