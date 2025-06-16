package com.mtor.evolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DietaRequestDTO(
        @NotBlank(message = "Nome do protocolo é obrigatório")
        String nomeProtocolo,
        
        @NotNull(message = "Cliente ID é obrigatório")
        Long clienteId,
        
        LocalDate dataInicio,
        LocalDate dataFim,
        String objetivo,
        String observacoes,
        Boolean ativo
) {}