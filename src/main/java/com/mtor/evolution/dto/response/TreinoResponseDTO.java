package com.mtor.evolution.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TreinoResponseDTO {
    private Long id;
    private String nomeProtocolo;
    private String modalidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private String observacoes;
    private Boolean ativo;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}