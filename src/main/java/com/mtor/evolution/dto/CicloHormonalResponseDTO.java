package com.mtor.evolution.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CicloHormonalResponseDTO(
        Long id,
        String tipo,
        LocalDate dataInicio,
        LocalDate dataFim,
        String observacoesClinicas,
        Boolean ativo,
        Long clienteId,
        String clienteNome,
        Instant createdAt,
        Instant updatedAt
) {}