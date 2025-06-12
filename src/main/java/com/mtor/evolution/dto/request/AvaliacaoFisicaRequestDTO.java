package com.mtor.evolution.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvaliacaoFisicaRequestDTO {
    
    @NotNull(message = "Data da avaliação é obrigatória")
    private LocalDate dataAvaliacao;
    
    @NotNull(message = "Peso é obrigatório")
    @Positive(message = "Peso deve ser positivo")
    private Double peso;
    
    private Double imc;
    private Double percentualGordura;
    private Double massaMagra;
    private Double tmb;
    private Integer idadeBiologica;
    
    // Dobras cutâneas
    private Double dobraSubescapular;
    private Double dobraTricipital;
    private Double dobraBicipital;
    private Double dobraPeitoral;
    private Double dobraAxilarMedia;
    private Double dobraSupraIliaca;
    private Double dobraAbdominal;
    private Double dobraCoxa;
    
    // Perímetros
    private Double perimetroBracoDireito;
    private Double perimetroBracoEsquerdo;
    private Double perimetroCoxaDireita;
    private Double perimetroCoxaEsquerda;
    private Double perimetroCintura;
    private Double perimetroQuadril;
    private Double perimetroPescoco;
    private Double perimetroTronco;
    
    private String observacoes;
    
    @NotNull(message = "Cliente ID é obrigatório")
    private Long clienteId;
}