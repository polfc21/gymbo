package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface TokenService {
    Token createToken(User user);
    boolean isValid(String token);
    UsernamePasswordAuthenticationToken getAuthentication(String token);
    void revokeToken(String token);
}
