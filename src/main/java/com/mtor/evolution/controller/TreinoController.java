package com.mtor.evolution.controller;

import com.mtor.evolution.dto.TreinoRequestDTO;
import com.mtor.evolution.dto.TreinoResponseDTO;
import com.mtor.evolution.service.TreinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/treinos")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Treinos", description = "Gerenciamento de treinos e protocolos de exercícios")
public class TreinoController {

    private final TreinoService treinoService;

    @PostMapping
    @Operation(summary = "Criar novo treino")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<TreinoResponseDTO> criar(@Valid @RequestBody TreinoRequestDTO request) {
        TreinoResponseDTO treino = treinoService.criar(request);
        return ResponseEntity.ok(treino);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar treino por ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or hasRole('CLIENTE')")
    public ResponseEntity<TreinoResponseDTO> buscarPorId(@PathVariable Long id) {
        TreinoResponseDTO treino = treinoService.buscarPorId(id);
        return ResponseEntity.ok(treino);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar treinos por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<TreinoResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId, Pageable pageable) {
        Page<TreinoResponseDTO> treinos = treinoService.listarPorCliente(clienteId, pageable);
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/cliente/{clienteId}/ativos")
    @Operation(summary = "Listar treinos ativos por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<List<TreinoResponseDTO>> listarAtivosPorCliente(@PathVariable Long clienteId) {
        List<TreinoResponseDTO> treinos = treinoService.listarAtivosPorCliente(clienteId);
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/cliente/{clienteId}/buscar")
    @Operation(summary = "Buscar treinos por cliente e texto")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<TreinoResponseDTO>> buscarPorClienteETexto(
            @PathVariable Long clienteId, @RequestParam String search, Pageable pageable) {
        Page<TreinoResponseDTO> treinos = treinoService.buscarPorClienteETexto(clienteId, search, pageable);
        return ResponseEntity.ok(treinos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar treino")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<TreinoResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody TreinoRequestDTO request) {
        TreinoResponseDTO treino = treinoService.atualizar(id, request);
        return ResponseEntity.ok(treino);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar treino")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        treinoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}