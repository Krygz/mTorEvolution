package com.mtor.evolution.repository;

import com.mtor.evolution.model.CicloHormonal;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.enums.TipoCicloHormonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CicloHormonalRepository extends JpaRepository<CicloHormonal, Long> {
    
    List<CicloHormonal> findByClienteOrderByCreatedAtDesc(Cliente cliente);
    
    List<CicloHormonal> findByClienteAndAtivoTrue(Cliente cliente);
    
    List<CicloHormonal> findByTipoCiclo(TipoCicloHormonal tipoCiclo);
    
    @Query("SELECT c FROM CicloHormonal c WHERE c.cliente = :cliente AND c.ativo = true ORDER BY c.createdAt DESC LIMIT 1")
    Optional<CicloHormonal> findCurrentCicloByCliente(Cliente cliente);
    
    @Query("SELECT c FROM CicloHormonal c WHERE c.cliente.id = :clienteId ORDER BY c.createdAt DESC")
    List<CicloHormonal> findByClienteIdOrderByCreatedAtDesc(Long clienteId);
}