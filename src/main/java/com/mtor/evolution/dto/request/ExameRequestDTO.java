package com.mtor.evolution.dto.request;


import com.mtor.evolution.model.enums.TipoExame;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExameRequestDTO {
    
    @NotNull(message = "Tipo do exame é obrigatório")
    private TipoExame tipoExame;
    
    @NotNull(message = "Data de solicitação é obrigatória")
    private LocalDate dataSolicitacao;
    
    private LocalDate dataRealizacao;
    private String resultado;
    private Double valorNumerico;
    private String unidadeMedida;
    private String valorReferencia;
    private String observacoes;
    private String arquivePdfPath;
    
    @NotNull(message = "Cliente ID é obrigatório")
    private Long clienteId;
}