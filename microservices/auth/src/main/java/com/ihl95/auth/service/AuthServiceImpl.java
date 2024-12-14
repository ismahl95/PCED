package com.ihl95.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ihl95.auth.config.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService{

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthServiceImpl(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public String authenticate(String username, String password) {
    // Simular credenciales
    String storedPasswordHash = passwordEncoder.encode("password");

    if ("admin".equals(username) && passwordEncoder.matches(password, storedPasswordHash)) {
      return jwtTokenProvider.createToken(username);
    }

    throw new RuntimeException("Invalid credentials");
  }
}

