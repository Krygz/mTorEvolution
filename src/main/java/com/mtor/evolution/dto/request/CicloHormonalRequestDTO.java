package com.mtor.evolution.dto.request;

import com.mtor.evolution.model.enums.TipoCicloHormonal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CicloHormonalRequestDTO {
    
    @NotBlank(message = "Nome do ciclo é obrigatório")
    private String nomeCiclo;
    
    @NotNull(message = "Tipo do ciclo é obrigatório")
    private TipoCicloHormonal tipoCiclo;
    
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private String observacoesClinicas;
    private Boolean ativo = true;
    
    @NotNull(message = "Cliente ID é obrigatório")
    private Long clienteId;
}