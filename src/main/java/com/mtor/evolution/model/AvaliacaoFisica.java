package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes_fisicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoFisica extends BaseEntity {

    @Column(name = "data_avaliacao", nullable = false)
    private LocalDate dataAvaliacao;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "imc")
    private Double imc;

    @Column(name = "bf")
    private Double bf;

    @Column(name = "massa_magra")
    private Double massaMagra;

    @Column(name = "tmb")
    private Double tmb;

    @Column(name = "idade_biologica")
    private Integer idadeBiologica;

    // Dobras cutâneas (Pollock 7 dobras)
    @Column(name = "dobra_triceps")
    private Double dobraTriceps;

    @Column(name = "dobra_subescapular")
    private Double dobraSubescapular;

    @Column(name = "dobra_suprailiaca")
    private Double dobraSuprailiaca;

    @Column(name = "dobra_abdominal")
    private Double dobraAbdominal;

    @Column(name = "dobra_coxa")
    private Double dobraCoxa;

    @Column(name = "dobra_peitoral")
    private Double dobraPeitoral;

    @Column(name = "dobra_axilar")
    private Double dobraAxilar;

    // Perímetros
    @Column(name = "perimetro_braco")
    private Double perimetroBraco;

    @Column(name = "perimetro_coxa")
    private Double perimetroCoxa;

    @Column(name = "perimetro_cintura")
    private Double perimetroCintura;

    @Column(name = "perimetro_pescoco")
    private Double perimetroPescoco;

    @Column(name = "perimetro_quadril")
    private Double perimetroQuadril;

    @Column(name = "perimetro_panturrilha")
    private Double perimetroPanturrilha;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}