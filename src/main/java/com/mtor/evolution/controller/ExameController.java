package com.mtor.evolution.controller;

import com.mtor.evolution.dto.ExameRequestDTO;
import com.mtor.evolution.dto.ExameResponseDTO;
import com.mtor.evolution.service.ExameService;
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
@RequestMapping("/api/v1/exames")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Exames", description = "Gerenciamento de exames laboratoriais")
public class ExameController {

    private final ExameService exameService;

    @PostMapping
    @Operation(summary = "Criar novo exame")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<ExameResponseDTO> criar(@Valid @RequestBody ExameRequestDTO request) {
        ExameResponseDTO exame = exameService.criar(request);
        return ResponseEntity.ok(exame);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar exame por ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or hasRole('CLIENTE')")
    public ResponseEntity<ExameResponseDTO> buscarPorId(@PathVariable Long id) {
        ExameResponseDTO exame = exameService.buscarPorId(id);
        return ResponseEntity.ok(exame);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar exames por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<ExameResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId, Pageable pageable) {
        Page<ExameResponseDTO> exames = exameService.listarPorCliente(clienteId, pageable);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/cliente/{clienteId}/buscar")
    @Operation(summary = "Buscar exames por cliente e texto")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<ExameResponseDTO>> buscarPorClienteETexto(
            @PathVariable Long clienteId, @RequestParam String search, Pageable pageable) {
        Page<ExameResponseDTO> exames = exameService.buscarPorClienteETexto(clienteId, search, pageable);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/cliente/{clienteId}/ordenados")
    @Operation(summary = "Listar exames ordenados por data")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<List<ExameResponseDTO>> listarPorClienteOrdenado(@PathVariable Long clienteId) {
        List<ExameResponseDTO> exames = exameService.listarPorClienteOrdenado(clienteId);
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar exames por status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<List<ExameResponseDTO>> buscarPorStatus(@PathVariable String status) {
        List<ExameResponseDTO> exames = exameService.buscarPorStatus(status);
        return ResponseEntity.ok(exames);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exame")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<ExameResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody ExameRequestDTO request) {
        ExameResponseDTO exame = exameService.atualizar(id, request);
        return ResponseEntity.ok(exame);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar exame")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        exameService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}