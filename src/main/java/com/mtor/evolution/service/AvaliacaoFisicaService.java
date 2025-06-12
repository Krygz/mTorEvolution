package com.mtor.evolution.service;

import com.mtor.evolution.dto.request.AvaliacaoFisicaRequestDTO;
import com.mtor.evolution.dto.response.AvaliacaoFisicaResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.mapper.AvaliacaoFisicaMapper;
import com.mtor.evolution.model.AvaliacaoFisica;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.repository.AvaliacaoFisicaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AvaliacaoFisicaService {
    
    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final AvaliacaoFisicaMapper avaliacaoFisicaMapper;
    private final ClienteService clienteService;
    
    public AvaliacaoFisicaResponseDTO create(AvaliacaoFisicaRequestDTO requestDTO) {
        log.info("Criando nova avaliação física para cliente ID: {}", requestDTO.getClienteId());
        
        Cliente cliente = clienteService.findEntityById(requestDTO.getClienteId());
        
        AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaMapper.toEntity(requestDTO);
        avaliacaoFisica.setCliente(cliente);
        
        // Calcular IMC automaticamente se não fornecido
        if (avaliacaoFisica.getImc() == null && avaliacaoFisica.getPeso() != null && cliente.getAltura() != null) {
            double imc = avaliacaoFisica.getPeso() / (cliente.getAltura() * cliente.getAltura());
            avaliacaoFisica.setImc(Math.round(imc * 100.0) / 100.0);
        }
        
        AvaliacaoFisica savedAvaliacaoFisica = avaliacaoFisicaRepository.save(avaliacaoFisica);
        log.info("Avaliação física criada com sucesso: ID {}", savedAvaliacaoFisica.getId());
        
        return avaliacaoFisicaMapper.toResponseDTO(savedAvaliacaoFisica);
    }
    
    @Transactional(readOnly = true)
    public AvaliacaoFisicaResponseDTO findById(Long id) {
        log.info("Buscando avaliação física por ID: {}", id);
        AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação física não encontrada com ID: " + id));
        
        return avaliacaoFisicaMapper.toResponseDTO(avaliacaoFisica);
    }
    
    @Transactional(readOnly = true)
    public Page<AvaliacaoFisicaResponseDTO> findAll(Pageable pageable) {
        log.info("Buscando todas as avaliações físicas - Página: {}", pageable.getPageNumber());
        return avaliacaoFisicaRepository.findAll(pageable)
                .map(avaliacaoFisicaMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<AvaliacaoFisicaResponseDTO> findByClienteId(Long clienteId) {
        log.info("Buscando avaliações físicas por cliente ID: {}", clienteId);
        return avaliacaoFisicaRepository.findByClienteIdOrderByDataDesc(clienteId)
                .stream()
                .map(avaliacaoFisicaMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<AvaliacaoFisicaResponseDTO> findLatestByClienteId(Long clienteId) {
        log.info("Buscando última avaliação física do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return avaliacaoFisicaRepository.findLatestByCliente(cliente)
                .map(avaliacaoFisicaMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<AvaliacaoFisicaResponseDTO> findByClienteIdAndPeriod(Long clienteId, LocalDate inicio, LocalDate fim) {
        log.info("Buscando avaliações físicas do cliente ID: {} entre {} e {}", clienteId, inicio, fim);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return avaliacaoFisicaRepository.findByClienteAndDataAvaliacaoBetween(cliente, inicio, fim)
                .stream()
                .map(avaliacaoFisicaMapper::toResponseDTO)
                .toList();
    }
    
    public AvaliacaoFisicaResponseDTO update(Long id, AvaliacaoFisicaRequestDTO requestDTO) {
        log.info("Atualizando avaliação física ID: {}", id);
        
        AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação física não encontrada com ID: " + id));
        
        avaliacaoFisicaMapper.updateEntityFromDTO(requestDTO, avaliacaoFisica);
        
        // Recalcular IMC se necessário
        if (avaliacaoFisica.getImc() == null && avaliacaoFisica.getPeso() != null && avaliacaoFisica.getCliente().getAltura() != null) {
            double imc = avaliacaoFisica.getPeso() / (avaliacaoFisica.getCliente().getAltura() * avaliacaoFisica.getCliente().getAltura());
            avaliacaoFisica.setImc(Math.round(imc * 100.0) / 100.0);
        }
        
        AvaliacaoFisica updatedAvaliacaoFisica = avaliacaoFisicaRepository.save(avaliacaoFisica);
        log.info("Avaliação física atualizada com sucesso: ID {}", updatedAvaliacaoFisica.getId());
        
        return avaliacaoFisicaMapper.toResponseDTO(updatedAvaliacaoFisica);
    }
    
    public void delete(Long id) {
        log.info("Deletando avaliação física ID: {}", id);
        
        AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação física não encontrada com ID: " + id));
        
        avaliacaoFisicaRepository.delete(avaliacaoFisica);
        log.info("Avaliação física deletada com sucesso: ID {}", id);
    }
}