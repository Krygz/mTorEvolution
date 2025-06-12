package com.mtor.evolution.repository;

import com.mtor.evolution.model.AvaliacaoFisica;
import com.mtor.evolution.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {
    
    List<AvaliacaoFisica> findByClienteOrderByDataAvaliacaoDesc(Cliente cliente);
    
    @Query("SELECT a FROM AvaliacaoFisica a WHERE a.cliente = :cliente ORDER BY a.dataAvaliacao DESC LIMIT 1")
    Optional<AvaliacaoFisica> findLatestByCliente(Cliente cliente);
    
    List<AvaliacaoFisica> findByClienteAndDataAvaliacaoBetween(Cliente cliente, LocalDate inicio, LocalDate fim);
    
    @Query("SELECT a FROM AvaliacaoFisica a WHERE a.cliente.id = :clienteId ORDER BY a.dataAvaliacao DESC")
    List<AvaliacaoFisica> findByClienteIdOrderByDataDesc(Long clienteId);
}