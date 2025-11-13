package com.example.app;

import com.example.app.model.Role;
import com.example.app.model.UserEntity;
import com.example.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

/**
 * Creates a default admin user on startup if none exists.
 * Change username/password or remove this for production.
 */
@Configuration
public class InitDataRunner {

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new UserEntity("admin", encoder.encode("adminpass"), Set.of(Role.ROLE_ADMIN, Role.ROLE_USER)));
                System.out.println("Created default admin: admin / adminpass (change immediately)");
            }
        };
    }
}
