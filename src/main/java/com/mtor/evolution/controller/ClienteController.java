package com.mtor.evolution.controller;

import com.mtor.evolution.dto.ClienteRequestDTO;
import com.mtor.evolution.dto.ClienteResponseDTO;
import com.mtor.evolution.service.ClienteService;
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

@RestController
@RequestMapping("/api/v1/clientes")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<ClienteResponseDTO>> listarTodos(Pageable pageable) {
        Page<ClienteResponseDTO> clientes = clienteService.listarTodos(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar clientes por nome ou email")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarPorNomeOuEmail(
            @RequestParam String search, Pageable pageable) {
        Page<ClienteResponseDTO> clientes = clienteService.buscarPorNomeOuEmail(search, pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id, @Valid @RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO cliente = clienteService.atualizar(id, request);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}