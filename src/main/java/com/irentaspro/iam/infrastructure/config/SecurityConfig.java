package com.irentaspro.iam.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.irentaspro.iam.infrastructure.jwt.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(b -> b.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 🔓 Login / Register público
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        // 🔐 /me requiere token
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()

                        // 🔐 upgrade requiere token
                        .requestMatchers(HttpMethod.PUT, "/api/auth/upgrade").authenticated()

                        // 🔓 propiedades GET públicas
                        .requestMatchers(HttpMethod.GET, "/api/propiedades/**").permitAll()

                        // 🔐 POST/PUT/DELETE requieren token
                        .requestMatchers(HttpMethod.POST, "/api/propiedades/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/propiedades/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/propiedades/**").authenticated()

                        // 🔐 cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())

                .cors(c -> {
                }) // habilitar CORS

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {

        var config = new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
        config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
