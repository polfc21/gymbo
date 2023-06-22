package com.behabits.gymbo.infrastructure.config;

import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    void givenAuthenticatedEndpointsAndValidTokenThenSecurityContextHolderIsAuthenticated() throws Exception {
        String validToken = "Bearer VALID TOKEN";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UsernamePasswordAuthenticationToken auth = mock(UsernamePasswordAuthenticationToken.class);
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(this.jwtService);

        Mockito.when(request.getRequestURI()).thenReturn(ApiConstant.API_V1);
        when(request.getHeader("Authorization")).thenReturn(validToken);
        when(this.jwtService.isValid(validToken)).thenReturn(true);
        when(this.jwtService.getAuthentication(any(String.class))).thenReturn(auth);
        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication(), is(auth));
    }

    @Test
    void givenNonAuthenticatedEndpointsAndValidTokenThenOnlyDoFilterMethodIsCalled() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(this.jwtService);

        Mockito.when(request.getRequestURI()).thenReturn(ApiConstant.API_V1 + ApiConstant.AUTH);
        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(this.jwtService, times(0)).isValid(anyString());
        verify(this.jwtService, times(0)).getAuthentication(anyString());
    }

    @Test
    void testInvalidAuthorization() throws Exception {
        String invalidToken = "INVALID TOKEN";
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(this.jwtService);

        Mockito.when(request.getRequestURI()).thenReturn(ApiConstant.API_V1);
        when(request.getHeader("Authorization")).thenReturn(invalidToken);
        when(this.jwtService.isValid(invalidToken)).thenReturn(false);
        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(this.jwtService, times(1)).isValid(invalidToken);
        verify(this.jwtService, times(0)).getAuthentication(anyString());
    }
}
