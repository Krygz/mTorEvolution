package com.mtor.evolution.model;

import com.mtor.evolution.model.enums.TipoExame;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "exames")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exame extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_exame", nullable = false)
    private TipoExame tipoExame;
    
    @NotNull
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDate dataSolicitacao;
    
    @Column(name = "data_realizacao")
    private LocalDate dataRealizacao;
    
    @Column(name = "resultado", columnDefinition = "TEXT")
    private String resultado;
    
    @Column(name = "valor_numerico")
    private Double valorNumerico;
    
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    
    @Column(name = "valor_referencia")
    private String valorReferencia;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "arquivo_pdf_path")
    private String arquivePdfPath;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}