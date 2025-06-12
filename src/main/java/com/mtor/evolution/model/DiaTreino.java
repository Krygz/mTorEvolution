package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "dias_treino")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaTreino extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_dia", nullable = false)
    private String nomeDia; // Ex: "Segunda-feira - Deltoide e Tríceps"
    
    @Column(name = "dia_semana")
    private String diaSemana;
    
    @Column(name = "grupos_musculares")
    private String gruposMusculares;
    
    @Column(name = "ordem")
    private Integer ordem;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @OneToMany(mappedBy = "diaTreino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExercicioTreino> exercicios;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treino_id", nullable = false)
    private Treino treino;
}