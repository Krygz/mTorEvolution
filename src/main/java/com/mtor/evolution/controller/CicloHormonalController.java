package com.mtor.evolution.controller;

import com.mtor.evolution.dto.CicloHormonalRequestDTO;
import com.mtor.evolution.dto.CicloHormonalResponseDTO;
import com.mtor.evolution.service.CicloHormonalService;
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
@RequestMapping("/api/v1/ciclos-hormonais")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Ciclos Hormonais", description = "Gerenciamento de ciclos e protocolos hormonais")
public class CicloHormonalController {

    private final CicloHormonalService cicloHormonalService;

    @PostMapping
    @Operation(summary = "Criar novo ciclo hormonal")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<CicloHormonalResponseDTO> criar(@Valid @RequestBody CicloHormonalRequestDTO request) {
        CicloHormonalResponseDTO ciclo = cicloHormonalService.criar(request);
        return ResponseEntity.ok(ciclo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ciclo hormonal por ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or hasRole('CLIENTE')")
    public ResponseEntity<CicloHormonalResponseDTO> buscarPorId(@PathVariable Long id) {
        CicloHormonalResponseDTO ciclo = cicloHormonalService.buscarPorId(id);
        return ResponseEntity.ok(ciclo);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar ciclos hormonais por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<CicloHormonalResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId, Pageable pageable) {
        Page<CicloHormonalResponseDTO> ciclos = cicloHormonalService.listarPorCliente(clienteId, pageable);
        return ResponseEntity.ok(ciclos);
    }

    @GetMapping("/cliente/{clienteId}/ativos")
    @Operation(summary = "Listar ciclos hormonais ativos por cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<List<CicloHormonalResponseDTO>> listarAtivosPorCliente(@PathVariable Long clienteId) {
        List<CicloHormonalResponseDTO> ciclos = cicloHormonalService.listarAtivosPorCliente(clienteId);
        return ResponseEntity.ok(ciclos);
    }

    @GetMapping("/cliente/{clienteId}/buscar")
    @Operation(summary = "Buscar ciclos hormonais por cliente e texto")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #clienteId == authentication.principal.id)")
    public ResponseEntity<Page<CicloHormonalResponseDTO>> buscarPorClienteETexto(
            @PathVariable Long clienteId, @RequestParam String search, Pageable pageable) {
        Page<CicloHormonalResponseDTO> ciclos = cicloHormonalService.buscarPorClienteETexto(clienteId, search, pageable);
        return ResponseEntity.ok(ciclos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ciclo hormonal")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<CicloHormonalResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody CicloHormonalRequestDTO request) {
        CicloHormonalResponseDTO ciclo = cicloHormonalService.atualizar(id, request);
        return ResponseEntity.ok(ciclo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ciclo hormonal")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cicloHormonalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}