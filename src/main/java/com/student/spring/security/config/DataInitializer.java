package com.student.spring.security.config;

import com.student.spring.security.model.Role;
import com.student.spring.security.model.User;
import com.student.spring.security.repository.RoleRepository;
import com.student.spring.security.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    @Transactional
    public CommandLineRunner loadData(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Save the role
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
