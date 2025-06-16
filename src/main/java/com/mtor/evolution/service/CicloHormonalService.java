package com.mtor.evolution.service;

import com.mtor.evolution.dto.CicloHormonalRequestDTO;
import com.mtor.evolution.dto.CicloHormonalResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.model.CicloHormonal;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.repository.CicloHormonalRepository;
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
public class CicloHormonalService {

    private static final Logger log = LoggerFactory.getLogger(CicloHormonalService.class);

    private final CicloHormonalRepository cicloHormonalRepository;
    private final ClienteService clienteService;

    @Transactional
    public CicloHormonalResponseDTO criar(CicloHormonalRequestDTO request) {
        log.info("Criando novo ciclo hormonal para cliente ID: {}", request.clienteId());

        Cliente cliente = clienteService.buscarEntidadePorId(request.clienteId());

        CicloHormonal ciclo = CicloHormonal.builder()
                .tipo(request.tipo())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .observacoesClinicas(request.observacoesClinicas())
                .ativo(request.ativo() != null ? request.ativo() : true)
                .cliente(cliente)
                .build();

        CicloHormonal cicloSalvo = cicloHormonalRepository.save(ciclo);
        log.info("Ciclo hormonal criado com sucesso. ID: {}", cicloSalvo.getId());

        return toResponseDTO(cicloSalvo);
    }

    @Transactional(readOnly = true)
    public CicloHormonalResponseDTO buscarPorId(Long id) {
        log.info("Buscando ciclo hormonal por ID: {}", id);
        CicloHormonal ciclo = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado"));
        return toResponseDTO(ciclo);
    }

    @Transactional(readOnly = true)
    public Page<CicloHormonalResponseDTO> listarPorCliente(Long clienteId, Pageable pageable) {
        log.info("Listando ciclos hormonais do cliente ID: {}", clienteId);
        return cicloHormonalRepository.findByClienteId(clienteId, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<CicloHormonalResponseDTO> buscarPorClienteETexto(Long clienteId, String search, Pageable pageable) {
        log.info("Buscando ciclos hormonais do cliente ID: {} com texto: {}", clienteId, search);
        return cicloHormonalRepository.findByClienteIdAndSearch(clienteId, search, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<CicloHormonalResponseDTO> listarAtivosPorCliente(Long clienteId) {
        log.info("Listando ciclos hormonais ativos do cliente ID: {}", clienteId);
        return cicloHormonalRepository.findByClienteIdAndAtivoTrue(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public CicloHormonalResponseDTO atualizar(Long id, CicloHormonalRequestDTO request) {
        log.info("Atualizando ciclo hormonal ID: {}", id);

        CicloHormonal ciclo = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado"));

        ciclo.setTipo(request.tipo());
        ciclo.setDataInicio(request.dataInicio());
        ciclo.setDataFim(request.dataFim());
        ciclo.setObservacoesClinicas(request.observacoesClinicas());
        ciclo.setAtivo(request.ativo());

        CicloHormonal cicloAtualizado = cicloHormonalRepository.save(ciclo);
        log.info("Ciclo hormonal atualizado com sucesso. ID: {}", cicloAtualizado.getId());

        return toResponseDTO(cicloAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando ciclo hormonal ID: {}", id);

        CicloHormonal ciclo = cicloHormonalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciclo hormonal não encontrado"));

        ciclo.setAtivo(false);
        cicloHormonalRepository.save(ciclo);

        log.info("Ciclo hormonal desativado com sucesso. ID: {}", id);
    }

    private CicloHormonalResponseDTO toResponseDTO(CicloHormonal ciclo) {
        return new CicloHormonalResponseDTO(
                ciclo.getId(),
                ciclo.getTipo(),
                ciclo.getDataInicio(),
                ciclo.getDataFim(),
                ciclo.getObservacoesClinicas(),
                ciclo.getAtivo(),
                ciclo.getCliente().getId(),
                ciclo.getCliente().getNomeCompleto(),
                ciclo.getCreatedAt(),
                ciclo.getUpdatedAt()
        );
    }
}