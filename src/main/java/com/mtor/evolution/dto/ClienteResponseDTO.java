package com.mtor.evolution.dto;

import com.mtor.evolution.model.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;

public record ClienteResponseDTO(
        Long id,
        String nomeCompleto,
        String email,
        Integer idade,
        Double altura,
        LocalDate dataNascimento,
        Role role,
        Boolean ativo,
        @CreatedDate
        Instant createdAt,
        @LastModifiedDate
        Instant updatedAt
) {}