package com.mtor.evolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CicloHormonalRequestDTO(
        @NotBlank(message = "Tipo é obrigatório")
        String tipo,
        
        @NotNull(message = "Cliente ID é obrigatório")
        Long clienteId,
        
        LocalDate dataInicio,
        LocalDate dataFim,
        String observacoesClinicas,
        Boolean ativo
) {}