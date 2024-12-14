package com.ihl95.auth.dto;

public class JwtAuthenticationResponse {
    private String token;

    // Constructor
    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
