package com.student.spring.security.config;

import com.student.spring.security.util.JWTUtil;
import com.student.spring.security.repository.UserRepository;
import com.student.spring.security.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up Spring Security with JWT.
 *
 * This class defines beans related to authentication, authorization,
 * password encoding, and token-based security. It configures
 * a stateless security mechanism with JWT validation filter.
 */

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public SecurityConfig(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Bean definition for UserDetailsService which retrieves user details from the database.
     *
     * @return a custom UserDetailsService implementation
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRoles().stream()
                                .map(role -> role.getName())
                                .toArray(String[]::new))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Configures the security filter chain, including JWT authentication and route access rules.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while building the security chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JWTAuthenticationFilter(jwtUtil, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides an AuthenticationManager bean for authentication operations.
     *
     * @param config the AuthenticationConfiguration used to retrieve the manager
     * @return the AuthenticationManager bean
     * @throws Exception if an error occurs while retrieving the manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides a PasswordEncoder bean using BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
