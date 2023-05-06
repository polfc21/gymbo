package com.behabits.gymbo.infrastructure.config;

import com.behabits.gymbo.domain.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtAuthorizationFilterTest {

    @Mock
    private JwtService jwtService;

    @Test
    void testValidAuthorization() throws Exception {
        String validToken = "Bearer VALID TOKEN";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UsernamePasswordAuthenticationToken auth = mock(UsernamePasswordAuthenticationToken.class);
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(this.jwtService);

        when(request.getHeader("Authorization")).thenReturn(validToken);
        when(this.jwtService.getAuthentication("VALID TOKEN")).thenReturn(auth);
        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication(), is(auth));
    }

    @Test
    void testInvalidAuthorization() throws Exception {
        String invalidToken = "INVALID TOKEN";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(this.jwtService);

        when(request.getHeader("Authorization")).thenReturn(invalidToken);
        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(this.jwtService, times(0)).getAuthentication(anyString());
    }
}
