package com.mtor.evolution.service;

import com.mtor.evolution.dto.request.CicloHormonalRequestDTO;
import com.mtor.evolution.dto.response.CicloHormonalResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.mapper.CicloHormonalMapper;
import com.mtor.evolution.model.CicloHormonal;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.enums.TipoCicloHormonal;
import com.mtor.evolution.repository.CicloHormonalRepository;
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
public class CicloHormonalService {
    
    private final CicloHormonalRepository cicloHormonalRepository;
    private final CicloHormonalMapper cicloHormonalMapper;
    private final ClienteService clienteService;
    
    public CicloHormonalResponseDTO create(CicloHormonalRequestDTO requestDTO) {
        log.info("Criando novo ciclo hormonal para cliente ID: {}", requestDTO.getClienteId());
        
        Cliente cliente = clienteService.findEntityById(requestDTO.getClienteId());
        
        CicloHormonal cicloHormonal = cicloHormonalMapper.toEntity(requestDTO);
        cicloHormonal.setCliente(cliente);
        
        CicloHormonal savedCicloHormonal = cicloHormonalRepository.save(cicloHormonal);
        log.info("Ciclo hormonal criado com sucesso: ID {}", savedCicloHormonal.getId());
        
        return cicloHormonalMapper.toResponseDTO(savedCicloHormonal);
    }
    
    @Transactional(readOnly = true)
    public CicloHormonalResponseDTO findById(Long id) {
        log.info("Buscando ciclo hormonal por ID: {}", id);
        CicloHormonal cicloHormonal = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado com ID: " + id));
        
        return cicloHormonalMapper.toResponseDTO(cicloHormonal);
    }
    
    @Transactional(readOnly = true)
    public Page<CicloHormonalResponseDTO> findAll(Pageable pageable) {
        log.info("Buscando todos os ciclos hormonais - Página: {}", pageable.getPageNumber());
        return cicloHormonalRepository.findAll(pageable)
                .map(cicloHormonalMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<CicloHormonalResponseDTO> findByClienteId(Long clienteId) {
        log.info("Buscando ciclos hormonais por cliente ID: {}", clienteId);
        return cicloHormonalRepository.findByClienteIdOrderByCreatedAtDesc(clienteId)
                .stream()
                .map(cicloHormonalMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<CicloHormonalResponseDTO> findActiveCiclosByClienteId(Long clienteId) {
        log.info("Buscando ciclos hormonais ativos do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return cicloHormonalRepository.findByClienteAndAtivoTrue(cliente)
                .stream()
                .map(cicloHormonalMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<CicloHormonalResponseDTO> findCurrentCicloByClienteId(Long clienteId) {
        log.info("Buscando ciclo hormonal atual do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return cicloHormonalRepository.findCurrentCicloByCliente(cliente)
                .map(cicloHormonalMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<CicloHormonalResponseDTO> findByTipoCiclo(TipoCicloHormonal tipoCiclo) {
        log.info("Buscando ciclos hormonais por tipo: {}", tipoCiclo);
        return cicloHormonalRepository.findByTipoCiclo(tipoCiclo)
                .stream()
                .map(cicloHormonalMapper::toResponseDTO)
                .toList();
    }
    
    public CicloHormonalResponseDTO update(Long id, CicloHormonalRequestDTO requestDTO) {
        log.info("Atualizando ciclo hormonal ID: {}", id);
        
        CicloHormonal cicloHormonal = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado com ID: " + id));
        
        cicloHormonalMapper.updateEntityFromDTO(requestDTO, cicloHormonal);
        
        CicloHormonal updatedCicloHormonal = cicloHormonalRepository.save(cicloHormonal);
        log.info("Ciclo hormonal atualizado com sucesso: ID {}", updatedCicloHormonal.getId());
        
        return cicloHormonalMapper.toResponseDTO(updatedCicloHormonal);
    }
    
    public void delete(Long id) {
        log.info("Deletando ciclo hormonal ID: {}", id);
        
        CicloHormonal cicloHormonal = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado com ID: " + id));
        
        cicloHormonalRepository.delete(cicloHormonal);
        log.info("Ciclo hormonal deletado com sucesso: ID {}", id);
    }
    
    public CicloHormonalResponseDTO deactivate(Long id) {
        log.info("Desativando ciclo hormonal ID: {}", id);
        
        CicloHormonal cicloHormonal = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado com ID: " + id));
        
        cicloHormonal.setAtivo(false);
        CicloHormonal updatedCicloHormonal = cicloHormonalRepository.save(cicloHormonal);
        log.info("Ciclo hormonal desativado com sucesso: ID {}", id);
        
        return cicloHormonalMapper.toResponseDTO(updatedCicloHormonal);
    }
}