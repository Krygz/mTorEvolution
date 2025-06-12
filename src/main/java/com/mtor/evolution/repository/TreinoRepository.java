package com.mtor.evolution.repository;

import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    
    List<Treino> findByClienteOrderByCreatedAtDesc(Cliente cliente);
    
    List<Treino> findByClienteAndAtivoTrue(Cliente cliente);
    
    @Query("SELECT t FROM Treino t WHERE t.cliente = :cliente AND t.ativo = true ORDER BY t.createdAt DESC LIMIT 1")
    Optional<Treino> findCurrentTreinoByCliente(Cliente cliente);
    
    @Query("SELECT t FROM Treino t WHERE t.cliente.id = :clienteId ORDER BY t.createdAt DESC")
    List<Treino> findByClienteIdOrderByCreatedAtDesc(Long clienteId);
    
    List<Treino> findByModalidadeContainingIgnoreCase(String modalidade);
}