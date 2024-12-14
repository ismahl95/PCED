package com.ihl95.auth.controller;

import com.ihl95.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import com.ihl95.auth.dto.LoginRequest;
import com.ihl95.auth.dto.JwtAuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // Inyección de dependencia
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint para login, recibiendo el cuerpo de la solicitud como JSON
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // Verificar si la solicitud proviene de la Gateway
        String sourceHeader = request.getHeader("X-Source");
        if (sourceHeader == null || !sourceHeader.equals("gateway")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Si no viene de la Gateway, rechazar
        }

        try {
            // Autenticación del usuario y generación del JWT
            String token = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            
            // Crear la respuesta con el token
            JwtAuthenticationResponse response = new JwtAuthenticationResponse(token);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Si las credenciales son incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
