package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sessoes_treino")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessaoTreino extends BaseEntity {

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "grupo_muscular")
    private String grupoMuscular;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "sessaoTreino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercicio> exercicios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treino_id", nullable = false)
    private Treino treino;
}