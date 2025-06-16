package com.mtor.evolution.service;

import com.mtor.evolution.dto.ExameRequestDTO;
import com.mtor.evolution.dto.ExameResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.Exame;
import com.mtor.evolution.repository.ExameRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ExameService {

    private static final Logger log = LoggerFactory.getLogger(ExameService.class);

    private final ExameRepository exameRepository;
    private final ClienteService clienteService;

    @Transactional
    public ExameResponseDTO criar(ExameRequestDTO request) {
        log.info("Criando novo exame para cliente ID: {}", request.clienteId());

        Cliente cliente = clienteService.buscarEntidadePorId(request.clienteId());

        Exame exame = Exame.builder()
                .nomeExame(request.nomeExame())
                .dataSolicitacao(request.dataSolicitacao())
                .dataResultado(request.dataResultado())
                .resultado(request.resultado())
                .observacoes(request.observacoes())
                .arquivoUrl(request.arquivoUrl())
                .status(request.status() != null ? request.status() : "SOLICITADO")
                .cliente(cliente)
                .build();

        Exame exameSalvo = exameRepository.save(exame);
        log.info("Exame criado com sucesso. ID: {}", exameSalvo.getId());

        return toResponseDTO(exameSalvo);
    }

    @Transactional(readOnly = true)
    public ExameResponseDTO buscarPorId(Long id) {
        log.info("Buscando exame por ID: {}", id);
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado"));
        return toResponseDTO(exame);
    }

    @Transactional(readOnly = true)
    public Page<ExameResponseDTO> listarPorCliente(Long clienteId, Pageable pageable) {
        log.info("Listando exames do cliente ID: {}", clienteId);
        return exameRepository.findByClienteId(clienteId, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<ExameResponseDTO> buscarPorClienteETexto(Long clienteId, String search, Pageable pageable) {
        log.info("Buscando exames do cliente ID: {} com texto: {}", clienteId, search);
        return exameRepository.findByClienteIdAndSearch(clienteId, search, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<ExameResponseDTO> buscarPorStatus(String status) {
        log.info("Buscando exames por status: {}", status);
        return exameRepository.findByStatusOrderByDataSolicitacaoDesc(status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExameResponseDTO> listarPorClienteOrdenado(Long clienteId) {
        log.info("Listando exames ordenados do cliente ID: {}", clienteId);
        return exameRepository.findByClienteIdOrderByDataSolicitacaoDesc(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public ExameResponseDTO atualizar(Long id, ExameRequestDTO request) {
        log.info("Atualizando exame ID: {}", id);

        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exame não encontrado"));

        exame.setNomeExame(request.nomeExame());
        exame.setDataSolicitacao(request.dataSolicitacao());
        exame.setDataResultado(request.dataResultado());
        exame.setResultado(request.resultado());
        exame.setObservacoes(request.observacoes());
        exame.setArquivoUrl(request.arquivoUrl());
        exame.setStatus(request.status());

        Exame exameAtualizado = exameRepository.save(exame);
        log.info("Exame atualizado com sucesso. ID: {}", exameAtualizado.getId());

        return toResponseDTO(exameAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando exame ID: {}", id);

        if (!exameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exame não encontrado");
        }

        exameRepository.deleteById(id);
        log.info("Exame deletado com sucesso. ID: {}", id);
    }

    private ExameResponseDTO toResponseDTO(Exame exame) {
        return new ExameResponseDTO(
                exame.getId(),
                exame.getNomeExame(),
                exame.getDataSolicitacao(),
                exame.getDataResultado(),
                exame.getCliente().getId(),
                exame.getCliente().getNomeCompleto(),
                exame.getResultado(),
                exame.getObservacoes(),
                exame.getArquivoUrl(),
                exame.getStatus(),
                exame.getCreatedAt(),
                exame.getUpdatedAt()
        );
    }
}