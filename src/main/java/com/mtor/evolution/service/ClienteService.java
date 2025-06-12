package com.mtor.evolution.service;

import com.athletetrack.dto.request.ClienteRequestDTO;
import com.athletetrack.dto.response.ClienteResponseDTO;
import com.athletetrack.exception.ResourceNotFoundException;
import com.athletetrack.mapper.ClienteMapper;
import com.athletetrack.model.Cliente;
import com.athletetrack.model.enums.Role;
import com.athletetrack.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;
    
    public ClienteResponseDTO create(ClienteRequestDTO requestDTO) {
        log.info("Criando novo cliente: {}", requestDTO.getEmail());
        
        if (clienteRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        
        Cliente cliente = clienteMapper.toEntity(requestDTO);
        cliente.setSenha(passwordEncoder.encode(requestDTO.getSenha()));
        
        Cliente savedCliente = clienteRepository.save(cliente);
        log.info("Cliente criado com sucesso: ID {}", savedCliente.getId());
        
        return clienteMapper.toResponseDTO(savedCliente);
    }
    
    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        return clienteMapper.toResponseDTO(cliente);
    }
    
    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> findAll(Pageable pageable) {
        log.info("Buscando todos os clientes - Página: {}", pageable.getPageNumber());
        return clienteRepository.findAll(pageable)
                .map(clienteMapper::toResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findByRole(Role role) {
        log.info("Buscando clientes por role: {}", role);
        return clienteRepository.findByRole(role)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findActiveClientes() {
        log.info("Buscando clientes ativos");
        return clienteRepository.findByAtivoTrue()
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }
    
    public ClienteResponseDTO update(Long id, ClienteRequestDTO requestDTO) {
        log.info("Atualizando cliente ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        // Verificar se o email já está em uso por outro cliente
        if (!cliente.getEmail().equals(requestDTO.getEmail()) && 
            clienteRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        
        clienteMapper.updateEntityFromDTO(requestDTO, cliente);
        
        // Atualizar senha apenas se fornecida
        if (requestDTO.getSenha() != null && !requestDTO.getSenha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(requestDTO.getSenha()));
        }
        
        Cliente updatedCliente = clienteRepository.save(cliente);
        log.info("Cliente atualizado com sucesso: ID {}", updatedCliente.getId());
        
        return clienteMapper.toResponseDTO(updatedCliente);
    }
    
    public void delete(Long id) {
        log.info("Deletando cliente ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        clienteRepository.delete(cliente);
        log.info("Cliente deletado com sucesso: ID {}", id);
    }
    
    public void deactivate(Long id) {
        log.info("Desativando cliente ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
        log.info("Cliente desativado com sucesso: ID {}", id);
    }
    
    @Transactional(readOnly = true)
    public Cliente findEntityById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }
}