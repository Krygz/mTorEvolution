package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suplementos_dieta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuplementoDieta extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_suplemento", nullable = false)
    private String nomeSuplemento;
    
    @Column(name = "dosagem")
    private String dosagem;
    
    @Column(name = "frequencia")
    private String frequencia;
    
    @Column(name = "horario_recomendado")
    private String horarioRecomendado;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "manipulado")
    private Boolean manipulado = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieta_id", nullable = false)
    private Dieta dieta;
}