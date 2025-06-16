package com.mtor.evolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TreinoRequestDTO(
        @NotBlank(message = "Nome do protocolo é obrigatório")
        String nomeProtocolo,
        
        @NotNull(message = "Cliente ID é obrigatório")
        Long clienteId,
        
        String modalidade,
        LocalDate dataInicio,
        LocalDate dataFim,
        String observacoes,
        Boolean ativo
) {}