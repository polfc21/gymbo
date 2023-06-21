package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.services.AuthenticationService;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.domain.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public Token login(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticationResult = this.authenticationManager.authenticate(authentication);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authenticationResult.getPrincipal();
        String tokenString = this.jwtService.generateToken(userDetailsImpl);
        Token token = new Token(tokenString);
        token.setUser(userDetailsImpl.getUser());
        return this.tokenService.createToken(token);
    }
}
