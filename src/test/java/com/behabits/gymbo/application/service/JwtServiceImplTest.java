package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.application.jwt.JwtBuilder;
import com.behabits.gymbo.application.jwt.JwtParser;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.domain.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private JwtBuilder jwtBuilder;

    @Mock
    private JwtParser jwtParser;

    @Test
    void givenUserDetailsWhenGenerateTokenThenReturnTokenString() {
        UserDetails userDetails = new UserDetailsImpl(new UserModelRepository().getUser());
        String expectedToken = "EXPECTED TOKEN";

        when(this.jwtBuilder.buildToken(userDetails)).thenReturn(expectedToken);

        assertThat(this.jwtService.generateToken(userDetails), is(expectedToken));
    }

    @Test
    void givenTokenIsNullWhenIsValidThenReturnFalse() {
        assertThat(this.jwtService.isValid(null), is(false));
    }

    @Test
    void givenTokenIsNotBearerTokenWhenIsValidThenReturnFalse() {
        String notBearerToken = "NOT BEARER TOKEN";

        assertThat(this.jwtService.isValid(notBearerToken), is(false));
    }

    @Test
    void givenTokenIsBearerTokenAndUsernameIsNullWhenIsValidThenReturnFalse() {
        String bearerToken = "Bearer TOKEN";
        String token = bearerToken.substring(7);

        when(this.jwtParser.extractUsername(token)).thenReturn(null);

        assertThat(this.jwtService.isValid(bearerToken), is(false));
    }

    @Test
    void givenTokenIsBearerTokenAndUsernameIsNotSavedWhenIsValidThenThrowUsernameNotFoundException() {
        String bearerToken = "Bearer TOKEN";
        String token = bearerToken.substring(7);
        String username = "USERNAME";

        when(this.jwtParser.extractUsername(token)).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> this.jwtService.isValid(bearerToken));
    }

    @Test
    void givenTokenIsBearerTokenAndTokenIsInvalidWhenIsValidThenReturnFalse() {
        String bearerToken = "Bearer TOKEN";
        String token = bearerToken.substring(7);
        UserDetailsImpl userDetails = new UserDetailsImpl(new UserModelRepository().getUser());
        String username = userDetails.getUsername();

        when(this.jwtParser.extractUsername(token)).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(this.jwtParser.extractExpiration(token)).thenReturn(new Date());
        when(this.tokenService.isValid(token, userDetails.getUser())).thenReturn(false);

        assertThat(this.jwtService.isValid(bearerToken), is(false));
    }

    @Test
    void givenTokenIsBearerAndTokenIsValidAndIsExpiredWhenIsValidThenReturnFalse() {
        String bearerToken = "Bearer TOKEN";
        String token = bearerToken.substring(7);
        UserDetailsImpl userDetails = new UserDetailsImpl(new UserModelRepository().getUser());
        String username = userDetails.getUsername();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date expiredDate = calendar.getTime();

        when(this.jwtParser.extractUsername(token)).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(this.jwtParser.extractExpiration(token)).thenReturn(expiredDate);
        when(this.tokenService.isValid(token, userDetails.getUser())).thenReturn(true);

        assertThat(this.jwtService.isValid(bearerToken), is(false));
    }

    @Test
    void givenTokenIsBearerAndTokenIsValidAndIsNotExpiredWhenIsValidThenReturnTrue() {
        String bearerToken = "Bearer TOKEN";
        String token = bearerToken.substring(7);
        UserDetailsImpl userDetails = new UserDetailsImpl(new UserModelRepository().getUser());
        String username = userDetails.getUsername();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date nonExpiredDate = calendar.getTime();

        when(this.jwtParser.extractUsername(token)).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(this.jwtParser.extractExpiration(token)).thenReturn(nonExpiredDate);
        when(this.tokenService.isValid(token, userDetails.getUser())).thenReturn(true);

        assertThat(this.jwtService.isValid(bearerToken), is(true));
    }

    @Test
    void givenTokenWhenGetAuthenticationThenReturnUsernameAuthenticationToken() {
        String bearerToken = "Bearer TOKEN";
        String token = bearerToken.substring(7);
        UserDetails userDetails = new UserDetailsImpl(new UserModelRepository().getUser());
        String username = userDetails.getUsername();

        when(this.jwtParser.extractUsername(token)).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenReturn(userDetails);

        assertThat(this.jwtService.getAuthentication(bearerToken).getName(), is(username));
    }

}
