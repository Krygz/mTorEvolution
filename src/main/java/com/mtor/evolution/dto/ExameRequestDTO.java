package com.mtor.evolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExameRequestDTO(
        @NotBlank(message = "Nome do exame é obrigatório")
        String nomeExame,
        
        @NotNull(message = "Data de solicitação é obrigatória")
        LocalDate dataSolicitacao,
        
        @NotNull(message = "Cliente ID é obrigatório")
        Long clienteId,
        
        LocalDate dataResultado,
        String resultado,
        String observacoes,
        String arquivoUrl,
        String status
) {}