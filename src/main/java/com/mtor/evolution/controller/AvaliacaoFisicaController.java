package com.mtor.evolution.controller;

import com.mtor.evolution.dto.AvaliacaoFisicaRequestDTO;
import com.mtor.evolution.dto.AvaliacaoFisicaResponseDTO;
import com.mtor.evolution.service.AvaliacaoFisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avaliacoes-fisicas")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Avaliações Físicas", description = "Gerenciamento de avaliações físicas")
public class AvaliacaoFisicaController {

    private final AvaliacaoFisicaService avaliacaoFisicaService;

    @PostMapping
    @Operation(summary = "Criar nova avaliação física")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> criar(@Valid @RequestBody AvaliacaoFisicaRequestDTO request) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.criar(request);
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação física por ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or hasRole('CLIENTE')")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> buscarPorId(@PathVariable Long id) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.buscarPorId(id);
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar avaliações físicas por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<AvaliacaoFisicaResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId, Pageable pageable) {
        Page<AvaliacaoFisicaResponseDTO> avaliacoes = avaliacaoFisicaService.listarPorCliente(clienteId, pageable);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/cliente/{clienteId}/historico")
    @Operation(summary = "Buscar histórico de avaliações do cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<List<AvaliacaoFisicaResponseDTO>> buscarHistoricoPorCliente(@PathVariable Long clienteId) {
        List<AvaliacaoFisicaResponseDTO> historico = avaliacaoFisicaService.buscarHistoricoPorCliente(clienteId);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/cliente/{clienteId}/ultima")
    @Operation(summary = "Buscar última avaliação do cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> buscarUltimaAvaliacaoPorCliente(@PathVariable Long clienteId) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.buscarUltimaAvaliacaoPorCliente(clienteId);
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping("/cliente/{clienteId}/periodo")
    @Operation(summary = "Buscar avaliações por período")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<List<AvaliacaoFisicaResponseDTO>> buscarPorPeriodo(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<AvaliacaoFisicaResponseDTO> avaliacoes = avaliacaoFisicaService.buscarPorPeriodo(clienteId, dataInicio, dataFim);
        return ResponseEntity.ok(avaliacoes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação física")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody AvaliacaoFisicaRequestDTO request) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.atualizar(id, request);
        return ResponseEntity.ok(avaliacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliação física")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoFisicaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}