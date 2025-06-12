package com.mtor.evolution.controller;

import com.mtor.evolution.dto.request.ClienteRequestDTO;
import com.mtor.evolution.dto.response.ClienteResponseDTO;
import com.mtor.evolution.model.enums.Role;
import com.mtor.evolution.service.ClienteService;
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

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes com paginação")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<ClienteResponseDTO>> getAllClientes(Pageable pageable) {
        Page<ClienteResponseDTO> clientes = clienteService.findAll(pageable);
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Busca um cliente específico pelo ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) {
        ClienteResponseDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }
    
    @GetMapping("/role/{role}")
    @Operation(summary = "Buscar clientes por role", description = "Busca clientes por tipo de usuário")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<List<ClienteResponseDTO>> getClientesByRole(@PathVariable Role role) {
        List<ClienteResponseDTO> clientes = clienteService.findByRole(role);
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/ativos")
    @Operation(summary = "Listar clientes ativos", description = "Lista todos os clientes ativos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<List<ClienteResponseDTO>> getActiveClientes() {
        List<ClienteResponseDTO> clientes = clienteService.findActiveClientes();
        return ResponseEntity.ok(clientes);
    }
    
    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<ClienteResponseDTO> createCliente(@Valid @RequestBody ClienteRequestDTO clienteRequest) {
        ClienteResponseDTO cliente = clienteService.create(clienteRequest);
        return ResponseEntity.ok(cliente);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or (hasRole('CLIENTE') and #id == authentication.principal.id)")
    public ResponseEntity<ClienteResponseDTO> updateCliente(@PathVariable Long id, 
                                                           @Valid @RequestBody ClienteRequestDTO clienteRequest) {
        ClienteResponseDTO cliente = clienteService.update(id, clienteRequest);
        return ResponseEntity.ok(cliente);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar cliente", description = "Desativa um cliente sem removê-lo do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deactivateCliente(@PathVariable Long id) {
        clienteService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}