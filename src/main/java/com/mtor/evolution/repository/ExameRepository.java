package com.mtor.evolution.repository;

import com.mtor.evolution.model.Exame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    
    Page<Exame> findByClienteId(Long clienteId, Pageable pageable);
    
    List<Exame> findByClienteIdOrderByDataSolicitacaoDesc(Long clienteId);
    
    @Query("SELECT e FROM Exame e WHERE e.status = :status ORDER BY e.dataSolicitacao DESC")
    List<Exame> findByStatusOrderByDataSolicitacaoDesc(String status);
    
    @Query("SELECT e FROM Exame e WHERE e.cliente.id = :clienteId AND " +
           "(LOWER(e.nomeExame) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.status) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Exame> findByClienteIdAndSearch(Long clienteId, String search, Pageable pageable);
}