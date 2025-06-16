package com.mtor.evolution.security;

import com.mtor.evolution.model.Cliente;
import com.mtor.evolution.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        return UserPrincipal.create(cliente);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com id: " + id));

        return UserPrincipal.create(cliente);
    }
}