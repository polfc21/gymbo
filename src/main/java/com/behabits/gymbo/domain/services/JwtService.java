package com.behabits.gymbo.domain.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    Boolean isValid(String token);
    UsernamePasswordAuthenticationToken getAuthentication(String token);
}
