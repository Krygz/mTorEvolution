package com.mtor.evolution.dto.response;

import com.athletetrack.model.enums.TipoExame;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExameResponseDTO {
    private Long id;
    private TipoExame tipoExame;
    private LocalDate dataSolicitacao;
    private LocalDate dataRealizacao;
    private String resultado;
    private Double valorNumerico;
    private String unidadeMedida;
    private String valorReferencia;
    private String observacoes;
    private String arquivePdfPath;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}