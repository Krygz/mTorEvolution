package com.mtor.evolution.service;

import com.mtor.evolution.dto.request.ClienteRequestDTO;
import com.mtor.evolution.dto.request.LoginRequestDTO;
import com.mtor.evolution.dto.response.ClienteResponseDTO;
import com.mtor.evolution.dto.response.JwtResponseDTO;
import com.mtor.evolution.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final ClienteService clienteService;
    private final JwtUtils jwtUtils;
    
    public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        log.info("Autenticando usuário: {}", loginRequest.getEmail());
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getEmail());
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ClienteResponseDTO clienteResponse = clienteService.findByEmail(loginRequest.getEmail());
        
        log.info("Usuário autenticado com sucesso: {}", loginRequest.getEmail());
        
        return new JwtResponseDTO(jwt, refreshToken, clienteResponse.getId(), 
                clienteResponse.getEmail(), clienteResponse.getNomeCompleto(), 
                clienteResponse.getRole().toString());
    }
    
    public ClienteResponseDTO registerUser(ClienteRequestDTO signUpRequest) {
        log.info("Registrando novo usuário: {}", signUpRequest.getEmail());
        
        ClienteResponseDTO cliente = clienteService.create(signUpRequest);
        log.info("Usuário registrado com sucesso: {}", cliente.getEmail());
        
        return cliente;
    }
    
    public JwtResponseDTO refreshToken(String refreshToken) {
        log.info("Renovando token de acesso");
        
        if (jwtUtils.validateJwtToken(refreshToken)) {
            String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
            String newToken = jwtUtils.generateTokenFromUsername(username);
            String newRefreshToken = jwtUtils.generateRefreshToken(username);
            
            ClienteResponseDTO clienteResponse = clienteService.findByEmail(username);
            
            log.info("Token renovado com sucesso para usuário: {}", username);
            
            return new JwtResponseDTO(newToken, newRefreshToken, clienteResponse.getId(),
                    clienteResponse.getEmail(), clienteResponse.getNomeCompleto(),
                    clienteResponse.getRole().toString());
        } else {
            throw new IllegalArgumentException("Refresh token inválido");
        }
    }
}