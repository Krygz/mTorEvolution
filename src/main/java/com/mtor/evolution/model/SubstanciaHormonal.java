package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "substancias_hormonais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubstanciaHormonal extends BaseEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dosagem")
    private String dosagem;

    @Column(name = "frequencia_aplicacao")
    private String frequenciaAplicacao;

    @Column(name = "via_administracao")
    private String viaAdministracao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciclo_hormonal_id", nullable = false)
    private CicloHormonal cicloHormonal;
}