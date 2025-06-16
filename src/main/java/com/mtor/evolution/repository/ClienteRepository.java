package com.mtor.evolution.repository;


import com.mtor.evolution.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    Page<Cliente> findByAtivoTrue(Pageable pageable);
    
    @Query("SELECT c FROM Cliente c WHERE c.ativo = true AND " +
           "(LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Cliente> findByAtivoTrueAndSearch(String search, Pageable pageable);
}