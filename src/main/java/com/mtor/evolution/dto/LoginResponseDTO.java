package com.mtor.evolution.dto;

import com.mtor.evolution.model.enums.Role;

public record LoginResponseDTO(
        String token,
        String refreshToken,
        String tipo,
        Long id,
        String email,
        String nomeCompleto,
        Role role
) {}