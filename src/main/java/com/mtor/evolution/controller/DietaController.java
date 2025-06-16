package com.mtor.evolution.controller;

import com.mtor.evolution.dto.DietaRequestDTO;
import com.mtor.evolution.dto.DietaResponseDTO;
import com.mtor.evolution.service.DietaService;
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
@RequestMapping("/api/v1/dietas")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Dietas", description = "Gerenciamento de dietas e protocolos alimentares")
public class DietaController {

    private final DietaService dietaService;

    @PostMapping
    @Operation(summary = "Criar nova dieta")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<DietaResponseDTO> criar(@Valid @RequestBody DietaRequestDTO request) {
        DietaResponseDTO dieta = dietaService.criar(request);
        return ResponseEntity.ok(dieta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dieta por ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or hasRole('CLIENTE')")
    public ResponseEntity<DietaResponseDTO> buscarPorId(@PathVariable Long id) {
        DietaResponseDTO dieta = dietaService.buscarPorId(id);
        return ResponseEntity.ok(dieta);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar dietas por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<DietaResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId, Pageable pageable) {
        Page<DietaResponseDTO> dietas = dietaService.listarPorCliente(clienteId, pageable);
        return ResponseEntity.ok(dietas);
    }

    @GetMapping("/cliente/{clienteId}/ativas")
    @Operation(summary = "Listar dietas ativas por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<List<DietaResponseDTO>> listarAtivasPorCliente(@PathVariable Long clienteId) {
        List<DietaResponseDTO> dietas = dietaService.listarAtivasPorCliente(clienteId);
        return ResponseEntity.ok(dietas);
    }

    @GetMapping("/cliente/{clienteId}/buscar")
    @Operation(summary = "Buscar dietas por cliente e texto")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<DietaResponseDTO>> buscarPorClienteETexto(
            @PathVariable Long clienteId, @RequestParam String search, Pageable pageable) {
        Page<DietaResponseDTO> dietas = dietaService.buscarPorClienteETexto(clienteId, search, pageable);
        return ResponseEntity.ok(dietas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dieta")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<DietaResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody DietaRequestDTO request) {
        DietaResponseDTO dieta = dietaService.atualizar(id, request);
        return ResponseEntity.ok(dieta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar dieta")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        dietaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}