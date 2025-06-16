package com.mtor.evolution.service;

import com.mtor.evolution.dto.DietaRequestDTO;
import com.mtor.evolution.dto.DietaResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.Dieta;
import com.mtor.evolution.repository.DietaRepository;
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
public class DietaService {

    private static final Logger log = LoggerFactory.getLogger(DietaService.class);

    private final DietaRepository dietaRepository;
    private final ClienteService clienteService;

    @Transactional
    public DietaResponseDTO criar(DietaRequestDTO request) {
        log.info("Criando nova dieta para cliente ID: {}", request.clienteId());

        Cliente cliente = clienteService.buscarEntidadePorId(request.clienteId());

        Dieta dieta = Dieta.builder()
                .nomeProtocolo(request.nomeProtocolo())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .objetivo(request.objetivo())
                .observacoes(request.observacoes())
                .ativo(request.ativo() != null ? request.ativo() : true)
                .cliente(cliente)
                .build();

        Dieta dietaSalva = dietaRepository.save(dieta);
        log.info("Dieta criada com sucesso. ID: {}", dietaSalva.getId());

        return toResponseDTO(dietaSalva);
    }

    @Transactional(readOnly = true)
    public DietaResponseDTO buscarPorId(Long id) {
        log.info("Buscando dieta por ID: {}", id);
        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada"));
        return toResponseDTO(dieta);
    }

    @Transactional(readOnly = true)
    public Page<DietaResponseDTO> listarPorCliente(Long clienteId, Pageable pageable) {
        log.info("Listando dietas do cliente ID: {}", clienteId);
        return dietaRepository.findByClienteId(clienteId, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<DietaResponseDTO> buscarPorClienteETexto(Long clienteId, String search, Pageable pageable) {
        log.info("Buscando dietas do cliente ID: {} com texto: {}", clienteId, search);
        return dietaRepository.findByClienteIdAndSearch(clienteId, search, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<DietaResponseDTO> listarAtivasPorCliente(Long clienteId) {
        log.info("Listando dietas ativas do cliente ID: {}", clienteId);
        return dietaRepository.findByClienteIdAndAtivoTrue(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public DietaResponseDTO atualizar(Long id, DietaRequestDTO request) {
        log.info("Atualizando dieta ID: {}", id);

        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada"));

        dieta.setNomeProtocolo(request.nomeProtocolo());
        dieta.setDataInicio(request.dataInicio());
        dieta.setDataFim(request.dataFim());
        dieta.setObjetivo(request.objetivo());
        dieta.setObservacoes(request.observacoes());
        dieta.setAtivo(request.ativo());

        Dieta dietaAtualizada = dietaRepository.save(dieta);
        log.info("Dieta atualizada com sucesso. ID: {}", dietaAtualizada.getId());

        return toResponseDTO(dietaAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando dieta ID: {}", id);

        Dieta dieta = dietaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dieta não encontrada"));

        dieta.setAtivo(false);
        dietaRepository.save(dieta);

        log.info("Dieta desativada com sucesso. ID: {}", id);
    }

    private DietaResponseDTO toResponseDTO(Dieta dieta) {
        return new DietaResponseDTO(
                dieta.getId(),
                dieta.getNomeProtocolo(),
                dieta.getDataInicio(),
                dieta.getDataFim(),
                dieta.getObjetivo(),
                dieta.getObservacoes(),
                dieta.getAtivo(),
                dieta.getCliente().getId(),
                dieta.getCliente().getNomeCompleto(),
                dieta.getCreatedAt(),
                dieta.getUpdatedAt()
        );
    }
}