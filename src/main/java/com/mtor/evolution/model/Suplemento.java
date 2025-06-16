package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suplementos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suplemento extends BaseEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dosagem")
    private String dosagem;

    @Column(name = "frequencia")
    private String frequencia;

    @Column(name = "horario")
    private String horario;

    @Column(name = "tipo")
    private String tipo; // SUPLEMENTO, MANIPULADO

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dieta_id", nullable = false)
    private Dieta dieta;
}