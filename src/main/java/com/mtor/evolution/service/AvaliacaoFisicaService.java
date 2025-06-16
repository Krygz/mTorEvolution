package com.mtor.evolution.service;

import com.mtor.evolution.dto.AvaliacaoFisicaRequestDTO;
import com.mtor.evolution.dto.AvaliacaoFisicaResponseDTO;
import com.mtor.evolution.exception.ResourceNotFoundException;
import com.mtor.evolution.model.AvaliacaoFisica;
import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.repository.AvaliacaoFisicaRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AvaliacaoFisicaService {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoFisicaService.class);

    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final ClienteService clienteService;

    @Transactional
    public AvaliacaoFisicaResponseDTO criar(AvaliacaoFisicaRequestDTO request) {
        log.info("Criando nova avaliação física para cliente ID: {}", request.clienteId());

        Cliente cliente = clienteService.buscarEntidadePorId(request.clienteId());

        AvaliacaoFisica avaliacao = AvaliacaoFisica.builder()
                .dataAvaliacao(request.dataAvaliacao())
                .peso(request.peso())
                .imc(request.imc())
                .bf(request.bf())
                .massaMagra(request.massaMagra())
                .tmb(request.tmb())
                .idadeBiologica(request.idadeBiologica())
                .dobraTriceps(request.dobraTriceps())
                .dobraSubescapular(request.dobraSubescapular())
                .dobraSuprailiaca(request.dobraSuprailiaca())
                .dobraAbdominal(request.dobraAbdominal())
                .dobraCoxa(request.dobraCoxa())
                .dobraPeitoral(request.dobraPeitoral())
                .dobraAxilar(request.dobraAxilar())
                .perimetroBraco(request.perimetroBraco())
                .perimetroCoxa(request.perimetroCoxa())
                .perimetroCintura(request.perimetroCintura())
                .perimetroPescoco(request.perimetroPescoco())
                .perimetroQuadril(request.perimetroQuadril())
                .perimetroPanturrilha(request.perimetroPanturrilha())
                .observacoes(request.observacoes())
                .cliente(cliente)
                .build();

        AvaliacaoFisica avaliacaoSalva = avaliacaoFisicaRepository.save(avaliacao);
        log.info("Avaliação física criada com sucesso. ID: {}", avaliacaoSalva.getId());

        return toResponseDTO(avaliacaoSalva);
    }

    @Transactional(readOnly = true)
    public AvaliacaoFisicaResponseDTO buscarPorId(Long id) {
        log.info("Buscando avaliação física por ID: {}", id);
        AvaliacaoFisica avaliacao = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação física não encontrada"));
        return toResponseDTO(avaliacao);
    }

    @Transactional(readOnly = true)
    public Page<AvaliacaoFisicaResponseDTO> listarPorCliente(Long clienteId, Pageable pageable) {
        log.info("Listando avaliações físicas do cliente ID: {}", clienteId);
        return avaliacaoFisicaRepository.findByClienteId(clienteId, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoFisicaResponseDTO> buscarHistoricoPorCliente(Long clienteId) {
        log.info("Buscando histórico de avaliações do cliente ID: {}", clienteId);
        return avaliacaoFisicaRepository.findByClienteIdOrderByDataAvaliacaoDesc(clienteId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AvaliacaoFisicaResponseDTO buscarUltimaAvaliacaoPorCliente(Long clienteId) {
        log.info("Buscando última avaliação do cliente ID: {}", clienteId);
        return avaliacaoFisicaRepository.findFirstByClienteIdOrderByDataAvaliacaoDesc(clienteId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma avaliação encontrada para este cliente"));
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoFisicaResponseDTO> buscarPorPeriodo(Long clienteId, LocalDate dataInicio, LocalDate dataFim) {
        log.info("Buscando avaliações do cliente ID: {} entre {} e {}", clienteId, dataInicio, dataFim);
        return avaliacaoFisicaRepository.findByClienteIdAndDataAvaliacaoBetween(clienteId, dataInicio, dataFim)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public AvaliacaoFisicaResponseDTO atualizar(Long id, AvaliacaoFisicaRequestDTO request) {
        log.info("Atualizando avaliação física ID: {}", id);

        AvaliacaoFisica avaliacao = avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação física não encontrada"));

        avaliacao.setDataAvaliacao(request.dataAvaliacao());
        avaliacao.setPeso(request.peso());
        avaliacao.setImc(request.imc());
        avaliacao.setBf(request.bf());
        avaliacao.setMassaMagra(request.massaMagra());
        avaliacao.setTmb(request.tmb());
        avaliacao.setIdadeBiologica(request.idadeBiologica());
        avaliacao.setDobraTriceps(request.dobraTriceps());
        avaliacao.setDobraSubescapular(request.dobraSubescapular());
        avaliacao.setDobraSuprailiaca(request.dobraSuprailiaca());
        avaliacao.setDobraAbdominal(request.dobraAbdominal());
        avaliacao.setDobraCoxa(request.dobraCoxa());
        avaliacao.setDobraPeitoral(request.dobraPeitoral());
        avaliacao.setDobraAxilar(request.dobraAxilar());
        avaliacao.setPerimetroBraco(request.perimetroBraco());
        avaliacao.setPerimetroCoxa(request.perimetroCoxa());
        avaliacao.setPerimetroCintura(request.perimetroCintura());
        avaliacao.setPerimetroPescoco(request.perimetroPescoco());
        avaliacao.setPerimetroQuadril(request.perimetroQuadril());
        avaliacao.setPerimetroPanturrilha(request.perimetroPanturrilha());
        avaliacao.setObservacoes(request.observacoes());

        AvaliacaoFisica avaliacaoAtualizada = avaliacaoFisicaRepository.save(avaliacao);
        log.info("Avaliação física atualizada com sucesso. ID: {}", avaliacaoAtualizada.getId());

        return toResponseDTO(avaliacaoAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Deletando avaliação física ID: {}", id);

        if (!avaliacaoFisicaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avaliação física não encontrada");
        }

        avaliacaoFisicaRepository.deleteById(id);
        log.info("Avaliação física deletada com sucesso. ID: {}", id);
    }

    private AvaliacaoFisicaResponseDTO toResponseDTO(AvaliacaoFisica avaliacao) {
        return new AvaliacaoFisicaResponseDTO(
                avaliacao.getId(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getCliente().getId(),
                avaliacao.getCliente().getNomeCompleto(),
                avaliacao.getPeso(),
                avaliacao.getImc(),
                avaliacao.getBf(),
                avaliacao.getMassaMagra(),
                avaliacao.getTmb(),
                avaliacao.getIdadeBiologica(),
                avaliacao.getDobraTriceps(),
                avaliacao.getDobraSubescapular(),
                avaliacao.getDobraSuprailiaca(),
                avaliacao.getDobraAbdominal(),
                avaliacao.getDobraCoxa(),
                avaliacao.getDobraPeitoral(),
                avaliacao.getDobraAxilar(),
                avaliacao.getPerimetroBraco(),
                avaliacao.getPerimetroCoxa(),
                avaliacao.getPerimetroCintura(),
                avaliacao.getPerimetroPescoco(),
                avaliacao.getPerimetroQuadril(),
                avaliacao.getPerimetroPanturrilha(),
                avaliacao.getObservacoes(),
                avaliacao.getCreatedAt(),
                avaliacao.getUpdatedAt()
        );
    }
}