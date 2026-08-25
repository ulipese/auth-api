package com.soa.authapi.config;

import com.soa.authapi.model.Role;
import com.soa.authapi.model.User;
import com.soa.authapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Iniciando inicialização de dados...");
            if (userRepository.findByUsername("user").isEmpty()) {
                logger.info("Criando usuário comum: user");
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole(Role.USER);
                userRepository.save(user);
            }

            if (userRepository.findByUsername("admin").isEmpty()) {
                logger.info("Criando usuário administrador: admin");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }
            logger.info("Inicialização de dados concluída.");
        };
    }
}
