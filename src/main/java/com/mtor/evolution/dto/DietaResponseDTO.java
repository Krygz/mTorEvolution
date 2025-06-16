package com.mtor.evolution.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DietaResponseDTO(
        Long id,
        String nomeProtocolo,
        LocalDate dataInicio,
        LocalDate dataFim,
        String objetivo,
        String observacoes,
        Boolean ativo,
        Long clienteId,
        String clienteNome,
        Instant createdAt,
        Instant updatedAt
) {}