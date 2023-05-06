package com.behabits.gymbo.domain.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface JwtService {
    String createToken(String username, String email);
    UsernamePasswordAuthenticationToken getAuthentication(String token);
}
