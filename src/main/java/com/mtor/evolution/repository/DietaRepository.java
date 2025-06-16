package com.mtor.evolution.repository;

import com.mtor.evolution.model.Dieta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long> {
    
    Page<Dieta> findByClienteId(Long clienteId, Pageable pageable);
    
    List<Dieta> findByClienteIdAndAtivoTrue(Long clienteId);
    
//    Optional<Dieta> findByClienteIdAndAtivoTrue(Long clienteId, Boolean ativo);
    
    @Query("SELECT d FROM Dieta d WHERE d.cliente.id = :clienteId AND " +
           "(LOWER(d.nomeProtocolo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.objetivo) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Dieta> findByClienteIdAndSearch(Long clienteId, String search, Pageable pageable);
}