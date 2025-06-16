package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refeicoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refeicao extends BaseEntity {

    @Column(name = "nome_refeicao", nullable = false)
    private String nomeRefeicao;

    @Column(name = "horario")
    private String horario;

    @Column(name = "carboidratos", columnDefinition = "TEXT")
    private String carboidratos;

    @Column(name = "proteinas", columnDefinition = "TEXT")
    private String proteinas;

    @Column(name = "frutas", columnDefinition = "TEXT")
    private String frutas;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "suplementos_refeicao", columnDefinition = "TEXT")
    private String suplementosRefeicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieta_id", nullable = false)
    private Dieta dieta;
}