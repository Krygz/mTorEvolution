package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "refeicoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Refeicao extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_refeicao", nullable = false)
    private String nomeRefeicao; // Ex: "Café da manhã", "Almoço", "Jantar"
    
    @Column(name = "horario_sugerido")
    private LocalTime horarioSugerido;
    
    @Column(name = "carboidratos", columnDefinition = "TEXT")
    private String carboidratos;
    
    @Column(name = "proteinas", columnDefinition = "TEXT")
    private String proteinas;
    
    @Column(name = "frutas", columnDefinition = "TEXT")
    private String frutas;
    
    @Column(name = "vegetais", columnDefinition = "TEXT")
    private String vegetais;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "ordem")
    private Integer ordem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieta_id", nullable = false)
    private Dieta dieta;
}