package com.mtor.evolution.controller;

import com.athletetrack.dto.request.DietaRequestDTO;
import com.athletetrack.dto.response.DietaResponseDTO;
import com.athletetrack.service.DietaService;
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
@RequestMapping("/api/v1/dietas")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Dietas", description = "Endpoints para gerenciamento de dietas e protocolos nutricionais")
public class DietaController {
    
    private final DietaService dietaService;
    
    @GetMapping
    @Operation(summary = "Listar dietas", description = "Lista todas as dietas com paginação")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<DietaResponseDTO>> getAllDietas(Pageable pageable) {
        Page<DietaResponseDTO> dietas = dietaService.findAll(pageable);
        return ResponseEntity.ok(dietas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar dieta por ID", description = "Busca uma dieta específica pelo ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or @dietaService.findById(#id).clienteId == authentication.principal.id")
    public ResponseEntity<DietaResponseDTO> getDietaById(@PathVariable Long id) {
        DietaResponseDTO dieta = dietaService.findById(id);
        return ResponseEntity.ok(dieta);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar dietas por cliente", description = "Busca todas as dietas de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<DietaResponseDTO>> getDietasByClienteId(@PathVariable Long clienteId) {
        List<DietaResponseDTO> dietas = dietaService.findByClienteId(clienteId);
        return ResponseEntity.ok(dietas);
    }
    
    @GetMapping("/cliente/{clienteId}/ativas")
    @Operation(summary = "Buscar dietas ativas", description = "Busca dietas ativas de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<DietaResponseDTO>> getActiveDietasByClienteId(@PathVariable Long clienteId) {
        List<DietaResponseDTO> dietas = dietaService.findActiveDietasByClienteId(clienteId);
        return ResponseEntity.ok(dietas);
    }
    
    @GetMapping("/cliente/{clienteId}/atual")
    @Operation(summary = "Buscar dieta atual", description = "Busca a dieta atual de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<DietaResponseDTO> getCurrentDietaByClienteId(@PathVariable Long clienteId) {
        Optional<DietaResponseDTO> dieta = dietaService.findCurrentDietaByClienteId(clienteId);
        return dieta.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Criar dieta", description = "Cria uma nova dieta")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<DietaResponseDTO> createDieta(@Valid @RequestBody DietaRequestDTO dietaRequest) {
        DietaResponseDTO dieta = dietaService.create(dietaRequest);
        return ResponseEntity.ok(dieta);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dieta", description = "Atualiza os dados de uma dieta")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<DietaResponseDTO> updateDieta(@PathVariable Long id, 
                                                       @Valid @RequestBody DietaRequestDTO dietaRequest) {
        DietaResponseDTO dieta = dietaService.update(id, dietaRequest);
        return ResponseEntity.ok(dieta);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar dieta", description = "Remove uma dieta do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deleteDieta(@PathVariable Long id) {
        dietaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar dieta", description = "Desativa uma dieta sem removê-la do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<DietaResponseDTO> deactivateDieta(@PathVariable Long id) {
        DietaResponseDTO dieta = dietaService.deactivate(id);
        return ResponseEntity.ok(dieta);
    }
}