package com.mtor.evolution.controller;


import com.mtor.evolution.dto.ClienteRequestDTO;
import com.mtor.evolution.dto.ClienteResponseDTO;
import com.mtor.evolution.dto.LoginRequestDTO;
import com.mtor.evolution.dto.LoginResponseDTO;
import com.mtor.evolution.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<ClienteResponseDTO> registrar(@Valid @RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO response = authService.registrar(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Fazer login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token de acesso")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestParam String refreshToken) {
        LoginResponseDTO response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}