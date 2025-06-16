package com.mtor.evolution.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ClienteRequestDTO(
        @NotBlank(message = "Nome completo é obrigatório")
        String nomeCompleto,
        
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ter formato válido")
        String email,
        
        @NotBlank(message = "Senha é obrigatória")
        String senha,
        
        Integer idade,
        Double altura,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento
) {}