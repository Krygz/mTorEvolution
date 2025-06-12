package com.mtor.evolution.dto.response;


import com.mtor.evolution.model.enums.TipoCicloHormonal;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CicloHormonalResponseDTO {
    private Long id;
    private String nomeCiclo;
    private TipoCicloHormonal tipoCiclo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private String observacoesClinicas;
    private Boolean ativo;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}