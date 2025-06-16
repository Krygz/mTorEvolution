package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercicio extends BaseEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "metodo")
    private String metodo; // pirâmide, drop set, bi set, etc.

    @Column(name = "series")
    private Integer series;

    @Column(name = "repeticoes")
    private String repeticoes;

    @Column(name = "carga_sugerida")
    private String cargaSugerida;

    @Column(name = "descanso")
    private String descanso;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "ordem")
    private Integer ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_treino_id", nullable = false)
    private SessaoTreino sessaoTreino;
}