package com.student.spring.security.controller;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.student.spring.security.util.JWTUtil;

/**
 * REST Controller for handling user authentication.
 * 
 * Provides an endpoint to authenticate users and generate a JWT token upon 
 * successful login. The generated token includes user roles and is returned 
 * to the client to be used for authorized access to secured API endpoints.
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * POST /auth/login - Authenticates a user and generates a JWT token.
     *
     * @param username the username provided by the client
     * @param password the password provided by the client
     * @return a JWT token if authentication is successful, or an error response otherwise
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toSet());

        String token = jwtUtil.generateToken(username, roles);
        return ResponseEntity.ok(token);
    }
}
