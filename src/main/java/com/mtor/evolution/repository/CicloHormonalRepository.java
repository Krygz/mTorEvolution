package com.mtor.evolution.repository;

import com.mtor.evolution.model.CicloHormonal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CicloHormonalRepository extends JpaRepository<CicloHormonal, Long> {
    
    Page<CicloHormonal> findByClienteId(Long clienteId, Pageable pageable);
    
    List<CicloHormonal> findByClienteIdAndAtivoTrue(Long clienteId);
    
    @Query("SELECT c FROM CicloHormonal c WHERE c.cliente.id = :clienteId AND " +
           "(LOWER(c.tipo) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<CicloHormonal> findByClienteIdAndSearch(Long clienteId, String search, Pageable pageable);
}