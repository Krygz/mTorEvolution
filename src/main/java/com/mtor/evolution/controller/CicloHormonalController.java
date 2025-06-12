package com.mtor.evolution.controller;


import com.mtor.evolution.dto.request.CicloHormonalRequestDTO;
import com.mtor.evolution.dto.response.CicloHormonalResponseDTO;
import com.mtor.evolution.model.enums.TipoCicloHormonal;
import com.mtor.evolution.service.CicloHormonalService;
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
@RequestMapping("/api/v1/ciclos-hormonais")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Ciclos Hormonais", description = "Endpoints para gerenciamento de ciclos hormonais e protocolos")
public class CicloHormonalController {
    
    private final CicloHormonalService cicloHormonalService;
    
    @GetMapping
    @Operation(summary = "Listar ciclos hormonais", description = "Lista todos os ciclos hormonais com paginação")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Page<CicloHormonalResponseDTO>> getAllCiclosHormonais(Pageable pageable) {
        Page<CicloHormonalResponseDTO> ciclos = cicloHormonalService.findAll(pageable);
        return ResponseEntity.ok(ciclos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar ciclo hormonal por ID", description = "Busca um ciclo hormonal específico pelo ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or @cicloHormonalService.findById(#id).clienteId == authentication.principal.id")
    public ResponseEntity<CicloHormonalResponseDTO> getCicloHormonalById(@PathVariable Long id) {
        CicloHormonalResponseDTO ciclo = cicloHormonalService.findById(id);
        return ResponseEntity.ok(ciclo);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar ciclos por cliente", description = "Busca todos os ciclos hormonais de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<CicloHormonalResponseDTO>> getCiclosByClienteId(@PathVariable Long clienteId) {
        List<CicloHormonalResponseDTO> ciclos = cicloHormonalService.findByClienteId(clienteId);
        return ResponseEntity.ok(ciclos);
    }
    
    @GetMapping("/cliente/{clienteId}/ativos")
    @Operation(summary = "Buscar ciclos ativos", description = "Busca ciclos hormonais ativos de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<List<CicloHormonalResponseDTO>> getActiveCiclosByClienteId(@PathVariable Long clienteId) {
        List<CicloHormonalResponseDTO> ciclos = cicloHormonalService.findActiveCiclosByClienteId(clienteId);
        return ResponseEntity.ok(ciclos);
    }
    
    @GetMapping("/cliente/{clienteId}/atual")
    @Operation(summary = "Buscar ciclo atual", description = "Busca o ciclo hormonal atual de um cliente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH') or #clienteId == authentication.principal.id")
    public ResponseEntity<CicloHormonalResponseDTO> getCurrentCicloByClienteId(@PathVariable Long clienteId) {
        Optional<CicloHormonalResponseDTO> ciclo = cicloHormonalService.findCurrentCicloByClienteId(clienteId);
        return ciclo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/tipo/{tipoCiclo}")
    @Operation(summary = "Buscar ciclos por tipo", description = "Busca ciclos hormonais por tipo específico")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<List<CicloHormonalResponseDTO>> getCiclosByTipo(@PathVariable TipoCicloHormonal tipoCiclo) {
        List<CicloHormonalResponseDTO> ciclos = cicloHormonalService.findByTipoCiclo(tipoCiclo);
        return ResponseEntity.ok(ciclos);
    }
    
    @PostMapping
    @Operation(summary = "Criar ciclo hormonal", description = "Cria um novo ciclo hormonal")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<CicloHormonalResponseDTO> createCicloHormonal(@Valid @RequestBody CicloHormonalRequestDTO cicloRequest) {
        CicloHormonalResponseDTO ciclo = cicloHormonalService.create(cicloRequest);
        return ResponseEntity.ok(ciclo);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ciclo hormonal", description = "Atualiza os dados de um ciclo hormonal")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<CicloHormonalResponseDTO> updateCicloHormonal(@PathVariable Long id, 
                                                                       @Valid @RequestBody CicloHormonalRequestDTO cicloRequest) {
        CicloHormonalResponseDTO ciclo = cicloHormonalService.update(id, cicloRequest);
        return ResponseEntity.ok(ciclo);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ciclo hormonal", description = "Remove um ciclo hormonal do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<Void> deleteCicloHormonal(@PathVariable Long id) {
        cicloHormonalService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar ciclo hormonal", description = "Desativa um ciclo hormonal sem removê-lo do sistema")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COACH')")
    public ResponseEntity<CicloHormonalResponseDTO> deactivateCicloHormonal(@PathVariable Long id) {
        CicloHormonalResponseDTO ciclo =  cicloHormonalService.deactivate(id);
        return ResponseEntity.ok(ciclo);
    }
}