package com.mtor.evolution.controller;

import com.athletetrack.dto.request.TreinoRequestDTO;
import com.athletetrack.dto.response.TreinoResponseDTO;
import com.athletetrack.service.TreinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/treinos")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Treinos", description = "Endpoints para gerenciamento de treinos e protocolos de exercícios")
public class TreinoController {
    
    private final TreinoService treinoService;
    
    @GetMapping
    @Operation(summary = "Listar treinos", description = "Lista todos os treinos com paginação")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<TreinoResponseDTO>> getAllTreinos(Pageable pageable) {
        Page<TreinoResponseDTO> treinos = treinoService.findAll(pageable);
        return ResponseEntity.ok(treinos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar treino por ID", description = "Busca um treino específico pelo ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or @treinoService.findById(#id).clienteId == authentication.principal.id")
    public ResponseEntity<TreinoResponseDTO> getTreinoById(@PathVariable Long id) {
        TreinoResponseDTO treino = treinoService.findById(id);
        return ResponseEntity.ok(treino);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar treinos por cliente", description = "Busca todos os treinos de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<TreinoResponseDTO>> getTreinosByClienteId(@PathVariable Long clienteId) {
        List<TreinoResponseDTO> treinos = treinoService.findByClienteId(clienteId);
        return ResponseEntity.ok(treinos);
    }
    
    @GetMapping("/cliente/{clienteId}/ativos")
    @Operation(summary = "Buscar treinos ativos", description = "Busca treinos ativos de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<TreinoResponseDTO>> getActiveTreinosByClienteId(@PathVariable Long clienteId) {
        List<TreinoResponseDTO> treinos = treinoService.findActiveTreinosByClienteId(clienteId);
        return ResponseEntity.ok(treinos);
    }
    
    @GetMapping("/cliente/{clienteId}/atual")
    @Operation(summary = "Buscar treino atual", description = "Busca o treino atual de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<TreinoResponseDTO> getCurrentTreinoByClienteId(@PathVariable Long clienteId) {
        Optional<TreinoResponseDTO> treino = treinoService.findCurrentTreinoByClienteId(clienteId);
        return treino.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/modalidade/{modalidade}")
    @Operation(summary = "Buscar treinos por modalidade", description = "Busca treinos por modalidade específica")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<List<TreinoResponseDTO>> getTreinosByModalidade(@PathVariable String modalidade) {
        List<TreinoResponseDTO> treinos = treinoService.findByModalidade(modalidade);
        return ResponseEntity.ok(treinos);
    }
    
    @PostMapping
    @Operation(summary = "Criar treino", description = "Cria um novo treino")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<TreinoResponseDTO> createTreino(@Valid @RequestBody TreinoRequestDTO treinoRequest) {
        TreinoResponseDTO treino = treinoService.create(treinoRequest);
        return ResponseEntity.ok(treino);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar treino", description = "Atualiza os dados de um treino")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<TreinoResponseDTO> updateTreino(@PathVariable Long id, 
                                                         @Valid @RequestBody TreinoRequestDTO treinoRequest) {
        TreinoResponseDTO treino = treinoService.update(id, treinoRequest);
        return ResponseEntity.ok(treino);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar treino", description = "Remove um treino do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deleteTreino(@PathVariable Long id) {
        treinoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar treino", description = "Desativa um treino sem removê-lo do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<TreinoResponseDTO> deactivateTreino(@PathVariable Long id) {
        TreinoResponseDTO treino = treinoService.deactivate(id);
        return ResponseEntity.ok(treino);
    }
}