package com.mtor.evolution.service;

import com.mtor.evolution.dto.request.TreinoRequestDTO;
import com.mtor.evolution.dto.response.TreinoResponseDTO;
import com.mtor.evolution.mapper.TreinoMapper;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.repository.TreinoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TreinoService {
    
    private final TreinoRepository treinoRepository;
    private final TreinoMapper treinoMapper;
    private final ClienteService clienteService;
    
    public TreinoResponseDTO create(TreinoRequestDTO requestDTO) {
        log.info("Criando novo treino para cliente ID: {}", requestDTO.getClienteId());
        
        Cliente cliente = clienteService.findEntityById(requestDTO.getClienteId());
        
        Treino treino = treinoMapper.toEntity(requestDTO);
        treino.setCliente(cliente);
        
        Treino savedTreino = treinoRepository.save(treino);
        log.info("Treino criado com sucesso: ID {}", savedTreino.getId());
        
        return treinoMapper.toResponseDTO(savedTreino);
    }
    
    @Transactional(readOnly = true)
    public TreinoResponseDTO findById(Long id) {
        log.info("Buscando treino por ID: {}", id);
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com ID: " + id));
        
        return treinoMapper.toResponseDTO(treino);
    }
    
    @Transactional(readOnly = true)
    public Page<TreinoResponseDTO> findAll(Pageable pageable) {
        log.info("Buscando todos os treinos - Página: {}", pageable.getPageNumber());
        return treinoRepository.findAll(pageable)
                .map(treinoMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> findByClienteId(Long clienteId) {
        log.info("Buscando treinos por cliente ID: {}", clienteId);
        return treinoRepository.findByClienteIdOrderByCreatedAtDesc(clienteId)
                .stream()
                .map(treinoMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> findActiveTreinosByClienteId(Long clienteId) {
        log.info("Buscando treinos ativos do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return treinoRepository.findByClienteAndAtivoTrue(cliente)
                .stream()
                .map(treinoMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<TreinoResponseDTO> findCurrentTreinoByClienteId(Long clienteId) {
        log.info("Buscando treino atual do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return treinoRepository.findCurrentTreinoByCliente(cliente)
                .map(treinoMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> findByModalidade(String modalidade) {
        log.info("Buscando treinos por modalidade: {}", modalidade);
        return treinoRepository.findByModalidadeContainingIgnoreCase(modalidade)
                .stream()
                .map(treinoMapper::toResponseDTO)
                .toList();
    }
    
    public TreinoResponseDTO update(Long id, TreinoRequestDTO requestDTO) {
        log.info("Atualizando treino ID: {}", id);
        
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com ID: " + id));
        
        treinoMapper.updateEntityFromDTO(requestDTO, treino);
        
        Treino updatedTreino = treinoRepository.save(treino);
        log.info("Treino atualizado com sucesso: ID {}", updatedTreino.getId());
        
        return treinoMapper.toResponseDTO(updatedTreino);
    }
    
    public void delete(Long id) {
        log.info("Deletando treino ID: {}", id);
        
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com ID: " + id));
        
        treinoRepository.delete(treino);
        log.info("Treino deletado com sucesso: ID {}", id);
    }
    
    public TreinoResponseDTO deactivate(Long id) {
        log.info("Desativando treino ID: {}", id);
        
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado com ID: " + id));
        
        treino.setAtivo(false);
        Treino updatedTreino = treinoRepository.save(treino);
        log.info("Treino desativado com sucesso: ID {}", id);
        
        return treinoMapper.toResponseDTO(updatedTreino);
    }
}