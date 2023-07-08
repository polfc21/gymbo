package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServiceImplTest {

    @InjectMocks
    private LogoutServiceImpl logoutService;

    @Mock
    private TokenService tokenService;

    private static MockedStatic<SecurityContextHolder> securityContextHolder;

    @BeforeAll
    static void setUp() {
        securityContextHolder = mockStatic(SecurityContextHolder.class);
    }

    @AfterAll
    static void tearDown() {
        securityContextHolder.close();
    }

    @Test
    void givenCorrectBearerAuthorizationHeaderWhenLogoutThenRevokeTokenAndClearContext() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer testToken");
        doNothing().when(this.tokenService).revokeToken("testToken");

        this.logoutService.logout(request, response, authentication);

        securityContextHolder.verify(SecurityContextHolder::clearContext);
    }

    @Test
    void givenIncorrectBearerAuthorizationHeaderWhenLogoutThenDoNothing() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn("testToken");

        this.logoutService.logout(request, response, authentication);

        securityContextHolder.verify(SecurityContextHolder::clearContext, never());
    }
}
