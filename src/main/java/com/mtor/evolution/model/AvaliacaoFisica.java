package com.mtor.evolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes_fisicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFisica extends BaseEntity {
    
    @NotNull
    @Column(name = "data_avaliacao", nullable = false)
    private LocalDate dataAvaliacao;
    
    @NotNull
    @Column(nullable = false)
    private Double peso;
    
    @Column(name = "imc")
    private Double imc;
    
    @Column(name = "percentual_gordura")
    private Double percentualGordura;
    
    @Column(name = "massa_magra")
    private Double massaMagra;
    
    @Column(name = "tmb")
    private Double tmb; // Taxa Metabólica Basal
    
    @Column(name = "idade_biologica")
    private Integer idadeBiologica;
    
    // Dobras cutâneas (Pollock 7 dobras)
    @Column(name = "dobra_subescapular")
    private Double dobraSubescapular;
    
    @Column(name = "dobra_tricipital")
    private Double dobraTricipital;
    
    @Column(name = "dobra_bicipital")
    private Double dobraBicipital;
    
    @Column(name = "dobra_peitoral")
    private Double dobraPeitoral;
    
    @Column(name = "dobra_axilar_media")
    private Double dobraAxilarMedia;
    
    @Column(name = "dobra_supra_iliaca")
    private Double dobraSupraIliaca;
    
    @Column(name = "dobra_abdominal")
    private Double dobraAbdominal;
    
    @Column(name = "dobra_coxa")
    private Double dobraCoxa;
    
    // Perímetros
    @Column(name = "perimetro_braco_direito")
    private Double perimetroBracoDireito;
    
    @Column(name = "perimetro_braco_esquerdo")
    private Double perimetroBracoEsquerdo;
    
    @Column(name = "perimetro_coxa_direita")
    private Double perimetroCoxaDireita;
    
    @Column(name = "perimetro_coxa_esquerda")
    private Double perimetroCoxaEsquerda;
    
    @Column(name = "perimetro_cintura")
    private Double perimetroCintura;
    
    @Column(name = "perimetro_quadril")
    private Double perimetroQuadril;
    
    @Column(name = "perimetro_pescoco")
    private Double perimetroPescoco;
    
    @Column(name = "perimetro_tronco")
    private Double perimetroTronco;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}