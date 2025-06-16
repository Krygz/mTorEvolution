package com.mtor.evolution.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AvaliacaoFisicaResponseDTO(
        Long id,
        LocalDate dataAvaliacao,
        Long clienteId,
        String clienteNome,
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
        String observacoes,
        Instant createdAt,
        Instant updatedAt
) {}