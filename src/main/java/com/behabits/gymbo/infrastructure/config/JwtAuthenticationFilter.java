package com.behabits.gymbo.infrastructure.config;

import com.behabits.gymbo.application.domain.UserDetails;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.infrastructure.controller.dto.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest login = this.getLogin(request);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
        );
        return getAuthenticationManager().authenticate(authentication);
    }

    private LoginRequest getLogin(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getReader(), LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException("BAD LOGIN REQUEST");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String token = this.jwtService.createToken(userDetails.getUsername(), userDetails.getEmail());
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }

}
