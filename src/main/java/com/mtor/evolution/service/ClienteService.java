package com.mtor.evolution.service;

import com.mtor.evolution.dto.ClienteRequestDTO;
import com.mtor.evolution.dto.ClienteResponseDTO;
import com.mtor.evolution.exception.BusinessException;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.enums.Role;
import com.mtor.evolution.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO request) {
        log.info("Criando novo cliente com email: {}", request.email());

        if (clienteRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já está em uso");
        }

        Cliente cliente = Cliente.builder()
                .nomeCompleto(request.nomeCompleto())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .idade(request.idade())
                .altura(request.altura())
                .dataNascimento(request.dataNascimento())
                .role(Role.CLIENTE)
                .ativo(true)
                .build();

        Cliente clienteSalvo = clienteRepository.save(cliente);
        log.info("Cliente criado com sucesso. ID: {}", clienteSalvo.getId());

        return toResponseDTO(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return toResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listarTodos(Pageable pageable) {
        log.info("Listando todos os clientes ativos");
        return clienteRepository.findByAtivoTrue(pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> buscarPorNomeOuEmail(String search, Pageable pageable) {
        log.info("Buscando clientes por: {}", search);
        return clienteRepository.findByAtivoTrueAndSearch(search, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO request) {
        log.info("Atualizando cliente ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        if (!cliente.getEmail().equals(request.email()) && 
            clienteRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email já está em uso");
        }

        cliente.setNomeCompleto(request.nomeCompleto());
        cliente.setEmail(request.email());
        cliente.setIdade(request.idade());
        cliente.setAltura(request.altura());
        cliente.setDataNascimento(request.dataNascimento());

        if (request.senha() != null && !request.senha().isEmpty()) {
            cliente.setSenha(passwordEncoder.encode(request.senha()));
        }

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        log.info("Cliente atualizado com sucesso. ID: {}", clienteAtualizado.getId());

        return toResponseDTO(clienteAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando cliente ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        cliente.setAtivo(false);
        clienteRepository.save(cliente);

        log.info("Cliente desativado com sucesso. ID: {}", id);
    }

    @Transactional(readOnly = true)
    public Cliente buscarEntidadePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNomeCompleto(),
                cliente.getEmail(),
                cliente.getIdade(),
                cliente.getAltura(),
                cliente.getDataNascimento(),
                cliente.getRole(),
                cliente.getAtivo(),
                cliente.getCreatedAt(),
                cliente.getUpdatedAt()
        );
    }
}