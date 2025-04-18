package com.student.spring.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "mysecret";
    private static final long EXPIRATION_TIME = 864_000_00; // 1 day

    public String generateToken(String username, java.util.Set<String> roles) {
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles.stream().collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }

    public String getUsername(String token) {
        return validateToken(token).getSubject();
    }

    public java.util.List<String> getRoles(String token) {
        return validateToken(token).getClaim("roles").asList(String.class);
    }
}