package com.mtor.evolution.repository;

import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.Exame;
import com.mtor.evolution.model.enums.TipoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    
    List<Exame> findByClienteOrderByDataSolicitacaoDesc(Cliente cliente);
    
    List<Exame> findByClienteAndTipoExame(Cliente cliente, TipoExame tipoExame);
    
    List<Exame> findByClienteAndDataSolicitacaoBetween(Cliente cliente, LocalDate inicio, LocalDate fim);
    
    @Query("SELECT e FROM Exame e WHERE e.cliente.id = :clienteId AND e.dataRealizacao IS NOT NULL ORDER BY e.dataRealizacao DESC")
    List<Exame> findExamesRealizadosByClienteId(Long clienteId);
    
    @Query("SELECT e FROM Exame e WHERE e.cliente.id = :clienteId AND e.dataRealizacao IS NULL ORDER BY e.dataSolicitacao DESC")
    List<Exame> findExamesPendentesByClienteId(Long clienteId);
}