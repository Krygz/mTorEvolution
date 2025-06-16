package com.mtor.evolution.repository;


import com.mtor.evolution.model.AvaliacaoFisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {
    
    Page<AvaliacaoFisica> findByClienteId(Long clienteId, Pageable pageable);
    
    List<AvaliacaoFisica> findByClienteIdOrderByDataAvaliacaoDesc(Long clienteId);
    
    Optional<AvaliacaoFisica> findFirstByClienteIdOrderByDataAvaliacaoDesc(Long clienteId);
    
    @Query("SELECT a FROM AvaliacaoFisica a WHERE a.cliente.id = :clienteId AND " +
           "a.dataAvaliacao BETWEEN :dataInicio AND :dataFim ORDER BY a.dataAvaliacao DESC")
    List<AvaliacaoFisica> findByClienteIdAndDataAvaliacaoBetween(Long clienteId, LocalDate dataInicio, LocalDate dataFim);
}