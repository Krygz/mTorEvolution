package com.mtor.evolution.controller;

import com.athletetrack.dto.request.AvaliacaoFisicaRequestDTO;
import com.athletetrack.dto.response.AvaliacaoFisicaResponseDTO;
import com.athletetrack.service.AvaliacaoFisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/avaliacoes-fisicas")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Avaliações Físicas", description = "Endpoints para gerenciamento de avaliações físicas")
public class AvaliacaoFisicaController {
    
    private final AvaliacaoFisicaService avaliacaoFisicaService;
    
    @GetMapping
    @Operation(summary = "Listar avaliações físicas", description = "Lista todas as avaliações físicas com paginação")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<AvaliacaoFisicaResponseDTO>> getAllAvaliacoesFisicas(Pageable pageable) {
        Page<AvaliacaoFisicaResponseDTO> avaliacoes = avaliacaoFisicaService.findAll(pageable);
        return ResponseEntity.ok(avaliacoes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação física por ID", description = "Busca uma avaliação física específica pelo ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or @avaliacaoFisicaService.findById(#id).clienteId == authentication.principal.id")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> getAvaliacaoFisicaById(@PathVariable Long id) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.findById(id);
        return ResponseEntity.ok(avaliacao);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar avaliações por cliente", description = "Busca todas as avaliações físicas de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<AvaliacaoFisicaResponseDTO>> getAvaliacoesByClienteId(@PathVariable Long clienteId) {
        List<AvaliacaoFisicaResponseDTO> avaliacoes = avaliacaoFisicaService.findByClienteId(clienteId);
        return ResponseEntity.ok(avaliacoes);
    }
    
    @GetMapping("/cliente/{clienteId}/ultima")
    @Operation(summary = "Buscar última avaliação do cliente", description = "Busca a avaliação física mais recente de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> getLatestAvaliacaoByClienteId(@PathVariable Long clienteId) {
        Optional<AvaliacaoFisicaResponseDTO> avaliacao = avaliacaoFisicaService.findLatestByClienteId(clienteId);
        return avaliacao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cliente/{clienteId}/periodo")
    @Operation(summary = "Buscar avaliações por período", description = "Busca avaliações físicas de um cliente em um período específico")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<AvaliacaoFisicaResponseDTO>> getAvaliacoesByClienteIdAndPeriod(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<AvaliacaoFisicaResponseDTO> avaliacoes = avaliacaoFisicaService.findByClienteIdAndPeriod(clienteId, inicio, fim);
        return ResponseEntity.ok(avaliacoes);
    }
    
    @PostMapping
    @Operation(summary = "Criar avaliação física", description = "Cria uma nova avaliação física")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> createAvaliacaoFisica(@Valid @RequestBody AvaliacaoFisicaRequestDTO avaliacaoRequest) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.create(avaliacaoRequest);
        return ResponseEntity.ok(avaliacao);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação física", description = "Atualiza os dados de uma avaliação física")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<AvaliacaoFisicaResponseDTO> updateAvaliacaoFisica(@PathVariable Long id, 
                                                                           @Valid @RequestBody AvaliacaoFisicaRequestDTO avaliacaoRequest) {
        AvaliacaoFisicaResponseDTO avaliacao = avaliacaoFisicaService.update(id, avaliacaoRequest);
        return ResponseEntity.ok(avaliacao);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliação física", description = "Remove uma avaliação física do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deleteAvaliacaoFisica(@PathVariable Long id) {
        avaliacaoFisicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}