package com.mtor.evolution.model;

import com.athletetrack.model.enums.TipoCicloHormonal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ciclos_hormonais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CicloHormonal extends BaseEntity {
    
    @NotBlank
    @Column(name = "nome_ciclo", nullable = false)
    private String nomeCiclo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ciclo", nullable = false)
    private TipoCicloHormonal tipoCiclo;
    
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    
    @Column(name = "data_fim")
    private LocalDate dataFim;
    
    @Column(name = "objetivo", columnDefinition = "TEXT")
    private String objetivo;
    
    @Column(name = "observacoes_clinicas", columnDefinition = "TEXT")
    private String observacoesClinicas;
    
    @Column(name = "ativo")
    private Boolean ativo = true;
    
    @OneToMany(mappedBy = "cicloHormonal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubstanciaCiclo> substancias;
    
    @OneToMany(mappedBy = "cicloHormonal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProtetorHormonal> protetores;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}