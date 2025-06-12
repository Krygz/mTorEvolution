package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "treinos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Treino extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_protocolo", nullable = false)
    private String nomeProtocolo;
    
    @Column(name = "modalidade")
    private String modalidade; // musculação, corrida, funcional, etc.
    
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    
    @Column(name = "data_fim")
    private LocalDate dataFim;
    
    @Column(name = "objetivo", columnDefinition = "TEXT")
    private String objetivo;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "ativo")
    private Boolean ativo = true;
    
    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiaTreeino> diasTreino;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}