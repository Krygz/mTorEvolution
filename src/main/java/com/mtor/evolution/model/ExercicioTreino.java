package com.mtor.evolution.model;

import com.mtor.evolution.model.enums.MetodoTreino;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exercicios_treino")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExercicioTreino extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_exercicio", nullable = false)
    private String nomeExercicio;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_treino")
    private MetodoTreino metodoTreino;
    
    @Column(name = "series")
    private Integer series;
    
    @Column(name = "repeticoes")
    private String repeticoes; // Ex: "8-12", "até a falha"
    
    @Column(name = "carga_sugerida")
    private String cargaSugerida;
    
    @Column(name = "tempo_descanso")
    private String tempoDescanso; // Ex: "90s", "2min"
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "ordem")
    private Integer ordem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_treino_id", nullable = false)
    private DiaTreino diaTreino;
}