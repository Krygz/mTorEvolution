package com.mtor.evolution.repository;

import com.mtor.evolution.model.Treino;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    
    Page<Treino> findByClienteId(Long clienteId, Pageable pageable);
    
    List<Treino> findByClienteIdAndAtivoTrue(Long clienteId);
    
    @Query("SELECT t FROM Treino t WHERE t.cliente.id = :clienteId AND " +
           "(LOWER(t.nomeProtocolo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(t.modalidade) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Treino> findByClienteIdAndSearch(Long clienteId, String search, Pageable pageable);
}