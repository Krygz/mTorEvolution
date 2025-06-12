package com.mtor.evolution.controller;

import com.mtor.evolution.dto.request.ExameRequestDTO;
import com.mtor.evolution.dto.response.ExameResponseDTO;
import com.mtor.evolution.model.enums.TipoExame;
import com.mtor.evolution.service.ExameService;
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

@RestController
@RequestMapping("/api/v1/exames")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Exames", description = "Endpoints para gerenciamento de exames laboratoriais")
public class ExameController {
    
    private final ExameService exameService;
    
    @GetMapping
    @Operation(summary = "Listar exames", description = "Lista todos os exames com paginação")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<ExameResponseDTO>> getAllExames(Pageable pageable) {
        Page<ExameResponseDTO> exames = exameService.findAll(pageable);
        return ResponseEntity.ok(exames);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar exame por ID", description = "Busca um exame específico pelo ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or @exameService.findById(#id).clienteId == authentication.principal.id")
    public ResponseEntity<ExameResponseDTO> getExameById(@PathVariable Long id) {
        ExameResponseDTO exame = exameService.findById(id);
        return ResponseEntity.ok(exame);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar exames por cliente", description = "Busca todos os exames de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<ExameResponseDTO>> getExamesByClienteId(@PathVariable Long clienteId) {
        List<ExameResponseDTO> exames = exameService.findByClienteId(clienteId);
        return ResponseEntity.ok(exames);
    }
    
    @GetMapping("/cliente/{clienteId}/tipo/{tipoExame}")
    @Operation(summary = "Buscar exames por tipo", description = "Busca exames de um cliente por tipo específico")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<ExameResponseDTO>> getExamesByClienteIdAndTipo(@PathVariable Long clienteId, 
                                                                             @PathVariable TipoExame tipoExame) {
        List<ExameResponseDTO> exames = exameService.findByClienteIdAndTipoExame(clienteId, tipoExame);
        return ResponseEntity.ok(exames);
    }
    
    @GetMapping("/cliente/{clienteId}/realizados")
    @Operation(summary = "Buscar exames realizados", description = "Busca exames já realizados de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<ExameResponseDTO>> getExamesRealizadosByClienteId(@PathVariable Long clienteId) {
        List<ExameResponseDTO> exames = exameService.findExamesRealizadosByClienteId(clienteId);
        return ResponseEntity.ok(exames);
    }
    
    @GetMapping("/cliente/{clienteId}/pendentes")
    @Operation(summary = "Buscar exames pendentes", description = "Busca exames pendentes de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<ExameResponseDTO>> getExamesPendentesByClienteId(@PathVariable Long clienteId) {
        List<ExameResponseDTO> exames = exameService.findExamesPendentesByClienteId(clienteId);
        return ResponseEntity.ok(exames);
    }
    
    @GetMapping("/cliente/{clienteId}/periodo")
    @Operation(summary = "Buscar exames por período", description = "Busca exames de um cliente em um período específico")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<ExameResponseDTO>> getExamesByClienteIdAndPeriod(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<ExameResponseDTO> exames = exameService.findByClienteIdAndPeriod(clienteId, inicio, fim);
        return ResponseEntity.ok(exames);
    }
    
    @PostMapping
    @Operation(summary = "Criar exame", description = "Cria um novo exame")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<ExameResponseDTO> createExame(@Valid @RequestBody ExameRequestDTO exameRequest) {
        ExameResponseDTO exame = exameService.create(exameRequest);
        return ResponseEntity.ok(exame);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exame", description = "Atualiza os dados de um exame")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<ExameResponseDTO> updateExame(@PathVariable Long id, 
                                                       @Valid @RequestBody ExameRequestDTO exameRequest) {
        ExameResponseDTO exame = exameService.update(id, exameRequest);
        return ResponseEntity.ok(exame);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar exame", description = "Remove um exame do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deleteExame(@PathVariable Long id) {
        exameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}