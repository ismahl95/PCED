package com.ihl95.auth;

import com.ihl95.auth.controller.AuthController;
import com.ihl95.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import com.ihl95.auth.dto.LoginRequest;
import com.ihl95.auth.dto.JwtAuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private final AuthService authService = mock(AuthService.class);
    private final AuthController authController = new AuthController(authService);

    @Test
    void testLoginSuccessful() {
        // Preparar
        String username = "admin";
        String password = "password";
        String token = "dummy-jwt-token";

        // Simular que el servicio de autenticación retorna un token válido
        when(authService.authenticate(username, password)).thenReturn(token);

        // Crear la solicitud de login
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Crear un mock de HttpServletRequest
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        // Simular la cabecera X-Source para asegurar que la solicitud proviene de la Gateway
        when(mockRequest.getHeader("X-Source")).thenReturn("gateway");

        // Ejecutar el método de login
        ResponseEntity<JwtAuthenticationResponse> response = authController.login(loginRequest, mockRequest);

        // Verificar
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Verificar que el código de estado es 200 OK
        assertNotNull(response.getBody()); // Verificar que la respuesta no es nula
        assertEquals(token, response.getBody().getToken()); // Verificar que el token retornado es el esperado
    }

    @Test
    void testLoginUnauthorized() {
        // Preparar
        String username = "admin";
        String password = "wrongpassword";

        // Simular que el servicio lanza una excepción si las credenciales son incorrectas
        when(authService.authenticate(username, password)).thenThrow(new RuntimeException("Invalid credentials"));

        // Crear la solicitud de login
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Crear un mock de HttpServletRequest
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        // Simular la cabecera X-Source para asegurar que la solicitud proviene de la Gateway
        when(mockRequest.getHeader("X-Source")).thenReturn("gateway");

        // Ejecutar el método de login
        ResponseEntity<JwtAuthenticationResponse> response = authController.login(loginRequest, mockRequest);

        // Verificar
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()); // Verificar que el código de estado es 401
        assertNull(response.getBody()); // Verificar que la respuesta no contiene un body
    }

    // Este test simula un comportamiento donde no se puede acceder al recurso de login si no pasa por la Gateway.
    @Test
    void testInvalidAccess() {
        // Preparar
        String username = "admin";
        String password = "password";

        // Simular que el servicio de autenticación retorna un token válido
        when(authService.authenticate(username, password)).thenReturn("dummy-jwt-token");

        // Crear la solicitud de login
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Crear un mock de HttpServletRequest
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        // Simular que la cabecera "X-Source" no tiene el valor "gateway"
        when(mockRequest.getHeader("X-Source")).thenReturn("wrong-source");

        // Ejecutar el método de login
        ResponseEntity<JwtAuthenticationResponse> response = authController.login(loginRequest, mockRequest);

        // Verificar que la respuesta es 403 Forbidden si la cabecera no es la correcta
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}