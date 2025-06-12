package com.mtor.evolution.repository;

import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    List<Cliente> findByRole(Role role);
    
    List<Cliente> findByAtivoTrue();
    
    @Query("SELECT c FROM Cliente c WHERE c.nomeCompleto LIKE %:nome%")
    List<Cliente> findByNomeContaining(String nome);
    
    boolean existsByEmail(String email);
}