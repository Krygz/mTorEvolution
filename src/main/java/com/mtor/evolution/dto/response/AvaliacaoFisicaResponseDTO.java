package com.mtor.evolution.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AvaliacaoFisicaResponseDTO {
    private Long id;
    private LocalDate dataAvaliacao;
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
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}