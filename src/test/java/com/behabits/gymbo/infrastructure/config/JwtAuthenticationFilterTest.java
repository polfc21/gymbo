package com.behabits.gymbo.infrastructure.config;

import com.behabits.gymbo.application.domain.UserDetails;
import com.behabits.gymbo.domain.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Test
    void testSuccessfulAuthentication() throws Exception {
        String username = "USERNAME";
        String email = "EMAIL";
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDetails.getEmail()).thenReturn(email);
        Authentication auth = mock(Authentication.class);
        String token = "VALID TOKEN";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        FilterChain chain = mock(FilterChain.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(this.jwtService);

        when(auth.getPrincipal()).thenReturn(userDetails);
        when(this.jwtService.createToken(anyString(), anyString())).thenReturn(token);
        when(response.getWriter()).thenReturn(writer);
        filter.successfulAuthentication(request, response, chain, auth);

        verify(response).addHeader("Authorization", "Bearer " + token);
        verify(response.getWriter()).flush();
    }

    @Test
    void testAttemptAuthentication() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String requestBody = "{\"username\": \"user1\", \"password\": \"password1\"}";
        BufferedReader reader = new BufferedReader(new StringReader(requestBody));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(this.jwtService);
        Authentication authentication = new UsernamePasswordAuthenticationToken("user1", "password1", List.of());
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        filter.setAuthenticationManager(authenticationManager);

        when(request.getReader()).thenReturn(reader);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Authentication result = filter.attemptAuthentication(request, response);

        assertThat(result.getPrincipal(), is(authentication.getPrincipal()));
        assertThat(result.isAuthenticated(), is(authentication.isAuthenticated()));
        assertThat(result.isAuthenticated(), is(true));
    }

    @Test
    void testAttemptAuthenticationThrowsException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        BufferedReader reader = new BufferedReader(new StringReader("BAD LOGIN REQUEST"));
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(this.jwtService);

        when(request.getReader()).thenReturn(reader);

        assertThrows(RuntimeException.class, () -> filter.attemptAuthentication(request, response));
    }

}
