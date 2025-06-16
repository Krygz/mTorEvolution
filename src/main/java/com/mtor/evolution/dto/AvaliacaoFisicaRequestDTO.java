package com.mtor.evolution.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AvaliacaoFisicaRequestDTO(
        @NotNull(message = "Data da avaliação é obrigatória")
        LocalDate dataAvaliacao,
        
        @NotNull(message = "Cliente ID é obrigatório")
        Long clienteId,
        
        Double peso,
        Double imc,
        Double bf,
        Double massaMagra,
        Double tmb,
        Integer idadeBiologica,
        Double dobraTriceps,
        Double dobraSubescapular,
        Double dobraSuprailiaca,
        Double dobraAbdominal,
        Double dobraCoxa,
        Double dobraPeitoral,
        Double dobraAxilar,
        Double perimetroBraco,
        Double perimetroCoxa,
        Double perimetroCintura,
        Double perimetroPescoco,
        Double perimetroQuadril,
        Double perimetroPanturrilha,
        String observacoes
) {}