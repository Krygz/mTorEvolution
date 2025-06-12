package com.mtor.evolution.dto.response;

import com.athletetrack.model.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nomeCompleto;
    private String email;
    private Integer idade;
    private Double altura;
    private LocalDate dataNascimento;
    private String telefone;
    private Role role;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}