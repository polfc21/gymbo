package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.jwt.JwtParser;
import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtParser jwtParser;

    @Override
    public Boolean isValid(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }
        String bearerToken = token.substring(7);
        String username = this.jwtParser.extractUsername(bearerToken);
        if (username == null) {
            return false;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) this.userService.loadUserByUsername(username);
        Date expirationDate = this.jwtParser.extractExpiration(bearerToken);
        return this.tokenService.isValid(bearerToken, userDetails.getUser()) && !this.isExpired(expirationDate);
    }

    private Boolean isExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String bearerToken = token.substring(7);
        String username = this.jwtParser.extractUsername(bearerToken);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) this.userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetailsImpl.getUsername(), bearerToken, Collections.emptyList());
    }

}
