package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ciclos_hormonais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CicloHormonal extends BaseEntity {

    @Column(name = "tipo", nullable = false)
    private String tipo; // suprafisiológico, exógeno, TRT

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "observacoes_clinicas", columnDefinition = "TEXT")
    private String observacoesClinicas;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @OneToMany(mappedBy = "cicloHormonal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubstanciaHormonal> substancias;

    @OneToMany(mappedBy = "cicloHormonal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProtetorHormonal> protetores;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}