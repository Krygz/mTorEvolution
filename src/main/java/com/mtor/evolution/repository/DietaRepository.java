package com.mtor.evolution.repository;

import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long> {
    
    List<Dieta> findByClienteOrderByCreatedAtDesc(Cliente cliente);
    
    List<Dieta> findByClienteAndAtivaTrue(Cliente cliente);
    
    @Query("SELECT d FROM Dieta d WHERE d.cliente = :cliente AND d.ativa = true ORDER BY d.createdAt DESC LIMIT 1")
    Optional<Dieta> findCurrentDietaByCliente(Cliente cliente);
    
    @Query("SELECT d FROM Dieta d WHERE d.cliente.id = :clienteId ORDER BY d.createdAt DESC")
    List<Dieta> findByClienteIdOrderByCreatedAtDesc(Long clienteId);
}