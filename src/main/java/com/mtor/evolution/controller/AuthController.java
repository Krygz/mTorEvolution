package com.mtor.evolution.controller;

import com.athletetrack.dto.request.ClienteRequestDTO;
import com.athletetrack.dto.request.LoginRequestDTO;
import com.athletetrack.dto.response.ClienteResponseDTO;
import com.athletetrack.dto.response.JwtResponseDTO;
import com.athletetrack.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica um usuário e retorna o token JWT")
    public ResponseEntity<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        JwtResponseDTO jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Registra um novo usuário no sistema")
    public ResponseEntity<ClienteResponseDTO> registerUser(@Valid @RequestBody ClienteRequestDTO signUpRequest) {
        ClienteResponseDTO cliente = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(cliente);
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Renova o token JWT usando o refresh token")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        JwtResponseDTO jwtResponse = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(jwtResponse);
    }
}