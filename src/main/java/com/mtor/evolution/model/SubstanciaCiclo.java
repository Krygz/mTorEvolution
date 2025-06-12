package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "substancias_ciclo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubstanciaCiclo extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_substancia", nullable = false)
    private String nomeSubstancia;
    
    @Column(name = "dosagem")
    private String dosagem;
    
    @Column(name = "frequencia_aplicacao")
    private String frequenciaAplicacao;
    
    @Column(name = "via_administracao")
    private String viaAdministracao; // Intramuscular, oral, etc.
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciclo_hormonal_id", nullable = false)
    private CicloHormonal cicloHormonal;
}