package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "protetores_hormonais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtetorHormonal extends BaseEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dosagem")
    private String dosagem;

    @Column(name = "frequencia")
    private String frequencia;

    @Column(name = "finalidade")
    private String finalidade;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciclo_hormonal_id", nullable = false)
    private CicloHormonal cicloHormonal;
}