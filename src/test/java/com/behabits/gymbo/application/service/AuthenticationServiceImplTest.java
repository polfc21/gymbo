package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Test
    void givenCorrectUsernameAndPasswordWhenLoginThenReturnToken() {
        String username = "testUser";
        String password = "testPassword";
        UserDetailsImpl userDetails = new UserDetailsImpl(new User());
        Token token = new Token();
        token.setUser(userDetails.getUser());

        Authentication authentication = Mockito.mock(Authentication.class);
        when(this.authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(this.tokenService.createToken(userDetails.getUser())).thenReturn(token);

        Token result = this.authenticationService.login(username, password);
        verify(this.authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        verify(this.tokenService).createToken(any(User.class));
        assertThat(result, is(token));
        assertThat(result.getUser(), is(userDetails.getUser()));
    }

    @Test
    void givenIncorrectUsernameAndPasswordWhenLoginThenThrowBadCredentialsException() {
        when(this.authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> this.authenticationService.login("testUser", "testPassword"));
    }
}
