package com.mtor.evolution.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TreinoRequestDTO {
    
    @NotBlank(message = "Nome do protocolo é obrigatório")
    private String nomeProtocolo;
    
    private String modalidade;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private String observacoes;
    private Boolean ativo = true;
    
    @NotNull(message = "Cliente ID é obrigatório")
    private Long clienteId;
}