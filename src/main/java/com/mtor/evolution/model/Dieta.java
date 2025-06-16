package com.mtor.evolution.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "dietas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dieta extends BaseEntity {

    @Column(name = "nome_protocolo", nullable = false)
    private String nomeProtocolo;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "objetivo")
    private String objetivo;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Refeicao> refeicoes;

    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Suplemento> suplementos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}