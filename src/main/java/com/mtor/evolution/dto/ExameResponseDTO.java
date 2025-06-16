package com.mtor.evolution.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExameResponseDTO(
        Long id,
        String nomeExame,
        LocalDate dataSolicitacao,
        LocalDate dataResultado,
        Long clienteId,
        String clienteNome,
        String resultado,
        String observacoes,
        String arquivoUrl,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}