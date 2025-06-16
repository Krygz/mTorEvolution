package com.mtor.evolution.service;

import com.mtor.evolution.dto.TreinoRequestDTO;
import com.mtor.evolution.dto.TreinoResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.model.Treino;
import com.mtor.evolution.repository.TreinoRepository;
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
public class TreinoService {

    private static final Logger log = LoggerFactory.getLogger(TreinoService.class);

    private final TreinoRepository treinoRepository;
    private final ClienteService clienteService;

    @Transactional
    public TreinoResponseDTO criar(TreinoRequestDTO request) {
        log.info("Criando novo treino para cliente ID: {}", request.clienteId());

        Cliente cliente = clienteService.buscarEntidadePorId(request.clienteId());

        Treino treino = Treino.builder()
                .nomeProtocolo(request.nomeProtocolo())
                .modalidade(request.modalidade())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .observacoes(request.observacoes())
                .ativo(request.ativo() != null ? request.ativo() : true)
                .cliente(cliente)
                .build();

        Treino treinoSalvo = treinoRepository.save(treino);
        log.info("Treino criado com sucesso. ID: {}", treinoSalvo.getId());

        return toResponseDTO(treinoSalvo);
    }

    @Transactional(readOnly = true)
    public TreinoResponseDTO buscarPorId(Long id) {
        log.info("Buscando treino por ID: {}", id);
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado"));
        return toResponseDTO(treino);
    }

    @Transactional(readOnly = true)
    public Page<TreinoResponseDTO> listarPorCliente(Long clienteId, Pageable pageable) {
        log.info("Listando treinos do cliente ID: {}", clienteId);
        return treinoRepository.findByClienteId(clienteId, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<TreinoResponseDTO> buscarPorClienteETexto(Long clienteId, String search, Pageable pageable) {
        log.info("Buscando treinos do cliente ID: {} com texto: {}", clienteId, search);
        return treinoRepository.findByClienteIdAndSearch(clienteId, search, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> listarAtivosPorCliente(Long clienteId) {
        log.info("Listando treinos ativos do cliente ID: {}", clienteId);
        return treinoRepository.findByClienteIdAndAtivoTrue(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public TreinoResponseDTO atualizar(Long id, TreinoRequestDTO request) {
        log.info("Atualizando treino ID: {}", id);

        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado"));

        treino.setNomeProtocolo(request.nomeProtocolo());
        treino.setModalidade(request.modalidade());
        treino.setDataInicio(request.dataInicio());
        treino.setDataFim(request.dataFim());
        treino.setObservacoes(request.observacoes());
        treino.setAtivo(request.ativo());

        Treino treinoAtualizado = treinoRepository.save(treino);
        log.info("Treino atualizado com sucesso. ID: {}", treinoAtualizado.getId());

        return toResponseDTO(treinoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando treino ID: {}", id);

        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino não encontrado"));

        treino.setAtivo(false);
        treinoRepository.save(treino);

        log.info("Treino desativado com sucesso. ID: {}", id);
    }

    private TreinoResponseDTO toResponseDTO(Treino treino) {
        return new TreinoResponseDTO(
                treino.getId(),
                treino.getNomeProtocolo(),
                treino.getModalidade(),
                treino.getDataInicio(),
                treino.getDataFim(),
                treino.getObservacoes(),
                treino.getAtivo(),
                treino.getCliente().getId(),
                treino.getCliente().getNomeCompleto(),
                treino.getCreatedAt(),
                treino.getUpdatedAt()
        );
    }
}