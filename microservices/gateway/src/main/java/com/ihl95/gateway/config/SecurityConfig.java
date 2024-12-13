package com.ihl95.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // Usualmente, la URL del JWKS es algo como:
        // "https://your-auth-server/.well-known/jwks.json"
        String jwkSetUri = "https://your-auth-server/.well-known/jwks.json";
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/**").permitAll() // Permitir acceso sin autenticación a /auth/**
                        .pathMatchers("/test/**").permitAll() // Permitir acceso sin autenticación a /test/**
                        .anyExchange().authenticated() // Todo lo demás requiere autenticación
                )
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt) // Configurar JWT
                .build();
    }
}
