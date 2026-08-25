package com.soa.authapi.service;

import com.soa.authapi.dto.LoginRequest;
import com.soa.authapi.dto.LoginResponse;
import com.soa.authapi.model.User;
import com.soa.authapi.repository.UserRepository;
import com.soa.authapi.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            AuthenticationProvider authenticationProvider,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse authenticate(LoginRequest input) {
        logger.info("Tentativa de login para o usuário: {}", input.username());
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        User user = userRepository.findByUsername(input.username())
                .orElseThrow(() -> {
                    logger.error("Usuário autenticado mas não encontrado no banco: {}", input.username());
                    return new RuntimeException("Erro interno ao recuperar usuário");
                });

        logger.info("Usuário {} autenticado com sucesso. Gerando token...", input.username());
        String jwtToken = jwtService.generateToken(user);

        return new LoginResponse(
                jwtToken, 
                "Bearer", 
                jwtService.getExpirationTime() / 1000,
                user.getUsername(),
                user.getRole().name()
        );
    }
}
