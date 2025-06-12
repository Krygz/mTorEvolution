package com.mtor.evolution.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String nomeCompleto;
    private String role;
    
    public JwtResponseDTO(String token, String refreshToken, Long id, String email, String nomeCompleto, String role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.role = role;
    }
}