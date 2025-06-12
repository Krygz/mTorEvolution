package com.mtor.evolution.service;

import com.athletetrack.dto.request.ExameRequestDTO;
import com.athletetrack.dto.response.ExameResponseDTO;
import com.athletetrack.exception.ResourceNotFoundException;
import com.athletetrack.mapper.ExameMapper;
import com.athletetrack.model.Cliente;
import com.athletetrack.model.Exame;
import com.athletetrack.model.enums.TipoExame;
import com.athletetrack.repository.ExameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExameService {
    
    private final ExameRepository exameRepository;
    private final ExameMapper exameMapper;
    private final ClienteService clienteService;
    
    public ExameResponseDTO create(ExameRequestDTO requestDTO) {
        log.info("Criando novo exame para cliente ID: {}", requestDTO.getClienteId());
        
        Cliente cliente = clienteService.findEntityById(requestDTO.getClienteId());
        
        Exame exame = exameMapper.toEntity(requestDTO);
        exame.setCliente(cliente);
        
        Exame savedExame = exameRepository.save(exame);
        log.info("Exame criado com sucesso: ID {}", savedExame.getId());
        
        return exameMapper.toResponseDTO(savedExame);
    }
    
    @Transactional(readOnly = true)
    public ExameResponseDTO findById(Long id) {
        log.info("Buscando exame por ID: {}", id);
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado com ID: " + id));
        
        return exameMapper.toResponseDTO(exame);
    }
    
    @Transactional(readOnly = true)
    public Page<ExameResponseDTO> findAll(Pageable pageable) {
        log.info("Buscando todos os exames - Página: {}", pageable.getPageNumber());
        return exameRepository.findAll(pageable)
                .map(exameMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<ExameResponseDTO> findByClienteId(Long clienteId) {
        log.info("Buscando exames por cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return exameRepository.findByClienteOrderByDataSolicitacaoDesc(cliente)
                .stream()
                .map(exameMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ExameResponseDTO> findByClienteIdAndTipoExame(Long clienteId, TipoExame tipoExame) {
        log.info("Buscando exames do cliente ID: {} do tipo: {}", clienteId, tipoExame);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return exameRepository.findByClienteAndTipoExame(cliente, tipoExame)
                .stream()
                .map(exameMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ExameResponseDTO> findExamesRealizadosByClienteId(Long clienteId) {
        log.info("Buscando exames realizados do cliente ID: {}", clienteId);
        return exameRepository.findExamesRealizadosByClienteId(clienteId)
                .stream()
                .map(exameMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ExameResponseDTO> findExamesPendentesByClienteId(Long clienteId) {
        log.info("Buscando exames pendentes do cliente ID: {}", clienteId);
        return exameRepository.findExamesPendentesByClienteId(clienteId)
                .stream()
                .map(exameMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ExameResponseDTO> findByClienteIdAndPeriod(Long clienteId, LocalDate inicio, LocalDate fim) {
        log.info("Buscando exames do cliente ID: {} entre {} e {}", clienteId, inicio, fim);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return exameRepository.findByClienteAndDataSolicitacaoBetween(cliente, inicio, fim)
                .stream()
                .map(exameMapper::toResponseDTO)
                .toList();
    }
    
    public ExameResponseDTO update(Long id, ExameRequestDTO requestDTO) {
        log.info("Atualizando exame ID: {}", id);
        
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado com ID: " + id));
        
        exameMapper.updateEntityFromDTO(requestDTO, exame);
        
        Exame updatedExame = exameRepository.save(exame);
        log.info("Exame atualizado com sucesso: ID {}", updatedExame.getId());
        
        return exameMapper.toResponseDTO(updatedExame);
    }
    
    public void delete(Long id) {
        log.info("Deletando exame ID: {}", id);
        
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado com ID: " + id));
        
        exameRepository.delete(exame);
        log.info("Exame deletado com sucesso: ID {}", id);
    }
}