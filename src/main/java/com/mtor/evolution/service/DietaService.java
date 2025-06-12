package com.mtor.evolution.service;

import com.athletetrack.dto.request.DietaRequestDTO;
import com.athletetrack.dto.response.DietaResponseDTO;
import com.athletetrack.exception.ResourceNotFoundException;
import com.athletetrack.mapper.DietaMapper;
import com.athletetrack.model.Cliente;
import com.athletetrack.model.Dieta;
import com.athletetrack.repository.DietaRepository;
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
public class DietaService {
    
    private final DietaRepository dietaRepository;
    private final DietaMapper dietaMapper;
    private final ClienteService clienteService;
    
    public DietaResponseDTO create(DietaRequestDTO requestDTO) {
        log.info("Criando nova dieta para cliente ID: {}", requestDTO.getClienteId());
        
        Cliente cliente = clienteService.findEntityById(requestDTO.getClienteId());
        
        Dieta dieta = dietaMapper.toEntity(requestDTO);
        dieta.setCliente(cliente);
        
        Dieta savedDieta = dietaRepository.save(dieta);
        log.info("Dieta criada com sucesso: ID {}", savedDieta.getId());
        
        return dietaMapper.toResponseDTO(savedDieta);
    }
    
    @Transactional(readOnly = true)
    public DietaResponseDTO findById(Long id) {
        log.info("Buscando dieta por ID: {}", id);
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada com ID: " + id));
        
        return dietaMapper.toResponseDTO(dieta);
    }
    
    @Transactional(readOnly = true)
    public Page<DietaResponseDTO> findAll(Pageable pageable) {
        log.info("Buscando todas as dietas - Página: {}", pageable.getPageNumber());
        return dietaRepository.findAll(pageable)
                .map(dietaMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<DietaResponseDTO> findByClienteId(Long clienteId) {
        log.info("Buscando dietas por cliente ID: {}", clienteId);
        return dietaRepository.findByClienteIdOrderByCreatedAtDesc(clienteId)
                .stream()
                .map(dietaMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<DietaResponseDTO> findActiveDietasByClienteId(Long clienteId) {
        log.info("Buscando dietas ativas do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return dietaRepository.findByClienteAndAtivaTrue(cliente)
                .stream()
                .map(dietaMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<DietaResponseDTO> findCurrentDietaByClienteId(Long clienteId) {
        log.info("Buscando dieta atual do cliente ID: {}", clienteId);
        Cliente cliente = clienteService.findEntityById(clienteId);
        
        return dietaRepository.findCurrentDietaByCliente(cliente)
                .map(dietaMapper::toResponseDTO);
    }
    
    public DietaResponseDTO update(Long id, DietaRequestDTO requestDTO) {
        log.info("Atualizando dieta ID: {}", id);
        
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada com ID: " + id));
        
        dietaMapper.updateEntityFromDTO(requestDTO, dieta);
        
        Dieta updatedDieta = dietaRepository.save(dieta);
        log.info("Dieta atualizada com sucesso: ID {}", updatedDieta.getId());
        
        return dietaMapper.toResponseDTO(updatedDieta);
    }
    
    public void delete(Long id) {
        log.info("Deletando dieta ID: {}", id);
        
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada com ID: " + id));
        
        dietaRepository.delete(dieta);
        log.info("Dieta deletada com sucesso: ID {}", id);
    }
    
    public DietaResponseDTO deactivate(Long id) {
        log.info("Desativando dieta ID: {}", id);
        
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada com ID: " + id));
        
        dieta.setAtiva(false);
        Dieta updatedDieta = dietaRepository.save(dieta);
        log.info("Dieta desativada com sucesso: ID {}", id);
        
        return dietaMapper.toResponseDTO(updatedDieta);
    }
}