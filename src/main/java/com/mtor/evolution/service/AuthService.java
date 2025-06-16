package com.mtor.evolution.service;


import com.mtor.evolution.dto.ClienteRequestDTO;
import com.mtor.evolution.dto.ClienteResponseDTO;
import com.mtor.evolution.dto.LoginRequestDTO;
import com.mtor.evolution.dto.LoginResponseDTO;
import com.mtor.evolution.exception.BusinessException;
import com.mtor.evolution.security.JwtTokenProvider;
import com.mtor.evolution.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final ClienteService clienteService;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public ClienteResponseDTO registrar(ClienteRequestDTO request) {
        log.info("Registrando novo usuário com email: {}", request.email());
        return clienteService.criar(request);
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        log.info("Tentativa de login para email: {}", request.email());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("Login realizado com sucesso para usuário ID: {}", userPrincipal.getId());

        return new LoginResponseDTO(
                jwt,
                refreshToken,
                "Bearer",
                userPrincipal.getId(),
                userPrincipal.getEmail(),
                userPrincipal.getUsername(),
                userPrincipal.getRole()
        );
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO refreshToken(String refreshToken) {
        log.info("Tentativa de refresh token");

        if (!tokenProvider.validateToken(refreshToken) || !tokenProvider.isRefreshToken(refreshToken)) {
            throw new BusinessException("Refresh token inválido");
        }

        Long userId = tokenProvider.getUserIdFromToken(refreshToken);
        ClienteResponseDTO cliente = clienteService.buscarPorId(userId);

        // Criar nova autenticação para gerar novos tokens
        UserPrincipal userPrincipal = new UserPrincipal(
                cliente.id(),
                cliente.email(),
                null, // senha não é necessária aqui
                cliente.role(),
                cliente.ativo()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities()
        );

        String newJwt = tokenProvider.generateToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

        log.info("Refresh token realizado com sucesso para usuário ID: {}", userId);

        return new LoginResponseDTO(
                newJwt,
                newRefreshToken,
                "Bearer",
                cliente.id(),
                cliente.email(),
                cliente.nomeCompleto(),
                cliente.role()
        );
    }
}