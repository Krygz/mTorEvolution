package com.mtor.evolution.dto.request;

import com.mtor.evolution.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequestDTO {
    
    @NotBlank(message = "Nome completo é obrigatório")
    private String nomeCompleto;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    
    @NotNull(message = "Idade é obrigatória")
    @Positive(message = "Idade deve ser positiva")
    private Integer idade;
    
    @NotNull(message = "Altura é obrigatória")
    @Positive(message = "Altura deve ser positiva")
    private Double altura;
    
    private LocalDate dataNascimento;
    private String telefone;
    private Role role = Role.CLIENTE;
    private Boolean ativo = true;
}