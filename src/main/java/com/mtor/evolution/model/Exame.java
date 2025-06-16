package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "exames")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exame extends BaseEntity {

    @Column(name = "nome_exame", nullable = false)
    private String nomeExame;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDate dataSolicitacao;

    @Column(name = "data_resultado")
    private LocalDate dataResultado;

    @Column(name = "resultado", columnDefinition = "TEXT")
    private String resultado;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "arquivo_url")
    private String arquivoUrl;

    @Column(name = "status")
    private String status = "SOLICITADO";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}