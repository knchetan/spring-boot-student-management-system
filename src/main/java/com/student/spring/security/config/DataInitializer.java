package com.student.spring.security.config;

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.student.spring.security.model.Role;
import com.student.spring.security.model.User;
import com.student.spring.security.repository.RoleRepository;
import com.student.spring.security.repository.UserRepository;
import jakarta.transaction.Transactional;

/**
 * Configuration class responsible for initializing data on application startup.
 * 
 * This initializer sets up default roles and an admin user if they do not already exist
 * in the database. It runs automatically when the Spring Boot application starts.
 */
@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    /**
     * Loads initial data into the database at application startup.
     * 
     * This method ensures that a default role ("ROLE_ADMIN") and an admin user
     * (username: "admin", password: "admin123") are created if they do not already exist.
     *
     * @param userRepository the repository for persisting user data
     * @param roleRepository the repository for persisting role data
     * @param passwordEncoder the password encoder for hashing passwords
     * @return a {@link CommandLineRunner} that executes the initialization logic
     */
    @Bean
    @Transactional
    public CommandLineRunner loadData(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleAdmin = roleRepository.save(roleAdmin);
    
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin123"));
                user.setRoles(Collections.singleton(roleAdmin));
                userRepository.save(user);
                logger.info("Admin user created: admin / admin123");
            }
        };
    }    
}

