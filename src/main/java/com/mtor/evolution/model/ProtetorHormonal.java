package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "protetores_hormonais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtetorHormonal extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_protetor", nullable = false)
    private String nomeProtetor;
    
    @Column(name = "dosagem")
    private String dosagem;
    
    @Column(name = "frequencia")
    private String frequencia;
    
    @Column(name = "finalidade")
    private String finalidade; // Ex: "Proteção hepática", "Controle estrógeno"
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciclo_hormonal_id", nullable = false)
    private CicloHormonal cicloHormonal;
}