package com.behabits.gymbo.domain.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface JwtService {
    Boolean isValid(String token);
    UsernamePasswordAuthenticationToken getAuthentication(String token);
}
