package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.application.jwt.JwtBuilder;
import com.behabits.gymbo.application.jwt.JwtParser;
import com.behabits.gymbo.domain.daos.TokenDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.TokenModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    private static final String BEARER_TOKEN = "Bearer token";
    private static final String TOKEN = "token";
    private static final String NOT_BEARER_TOKEN = "Not bearer token";
    private static final String USERNAME = "USERNAME";
    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenDao tokenDao;

    @Mock
    private JwtBuilder jwtBuilder;

    @Mock
    private JwtParser jwtParser;

    @Mock
    private UserService userService;

    private final Token token = new TokenModelRepository().getToken();
    private final User user = new UserModelRepository().getUser();
    private final UserDetailsImpl userDetails = new UserDetailsImpl(this.user);
    private Date nonExpiredDate;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        this.nonExpiredDate = calendar.getTime();
    }

    @Test
    void givenTokenAndUserTokensIsEmptyWhenCreateTokenThenReturnToken() {
        when(this.tokenDao.findAllTokensByUserId(this.user.getId())).thenReturn(List.of());
        when(this.jwtBuilder.buildToken(this.user)).thenReturn(this.token.getToken());
        when(this.tokenDao.createToken(any(Token.class))).thenReturn(this.token);

        verify(this.tokenDao, times(0)).saveAll(anyList());
        assertThat(this.tokenService.createToken(this.user), is(this.token));
    }

    @Test
    void givenTokenAndUserTokensIsNotEmptyWhenCreateTokenThenReturnToken() {
        when(this.tokenDao.findAllTokensByUserId(this.user.getId())).thenReturn(List.of(this.token));
        when(this.jwtBuilder.buildToken(this.user)).thenReturn(this.token.getToken());
        when(this.tokenDao.createToken(any(Token.class))).thenReturn(this.token);

        Token token = this.tokenService.createToken(this.user);

        verify(this.tokenDao, times(1)).saveAll(List.of(this.token));
        assertThat(token, is(this.token));
    }

    @Test
    void givenTokenIsNullWhenIsValidThenReturnFalse() {
        assertThat(this.tokenService.isValid(null), is(false));
    }

    @Test
    void givenTokenIsNotBearerTokenWhenIsValidThenReturnFalse() {
        assertThat(this.tokenService.isValid(NOT_BEARER_TOKEN), is(false));
    }

    @Test
    void givenTokenIsExpiredWhenIsValidThenReturnFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date expirationDate = calendar.getTime();

        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(expirationDate);

        assertThat(this.tokenService.isValid(BEARER_TOKEN), is(false));
    }

    @Test
    void givenTokenWithUsernameNullWhenIsValidThenReturnFalse() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(null);

        assertThat(this.tokenService.isValid(BEARER_TOKEN), is(false));
    }

    @Test
    void givenTokenWithUsernameNotSavedWhenIsValidThenThrowUsernameNotFoundException() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(USERNAME);
        when(this.userService.loadUserByUsername(USERNAME)).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> this.tokenService.isValid(BEARER_TOKEN));
    }

    @Test
    void givenTokenAndUserAreNotPresentWhenIsValidThenThrowPermissionsException() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(USERNAME);
        when(this.userService.loadUserByUsername(USERNAME)).thenReturn(this.userDetails);
        when(this.tokenDao.findByTokenAndUserId(TOKEN, this.user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.tokenService.isValid(BEARER_TOKEN));
    }

    @Test
    void givenSavedTokenIsExpiredWhenIsValidThenReturnFalse() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(USERNAME);
        when(this.userService.loadUserByUsername(USERNAME)).thenReturn(this.userDetails);
        when(this.tokenDao.findByTokenAndUserId(TOKEN, this.user.getId())).thenReturn(this.token);
        this.token.setIsExpired(true);

        assertThat(this.tokenService.isValid(BEARER_TOKEN), is(false));
    }

    @Test
    void givenSavedTokenIsRevokedWhenIsValidThenReturnFalse() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(USERNAME);
        when(this.userService.loadUserByUsername(USERNAME)).thenReturn(this.userDetails);
        when(this.tokenDao.findByTokenAndUserId(TOKEN, this.user.getId())).thenReturn(this.token);
        this.token.setIsRevoked(true);

        assertThat(this.tokenService.isValid(BEARER_TOKEN), is(false));
    }

    @Test
    void givenSavedTokenIsExpiredAndRevokedWhenIsValidThenReturnFalse() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(USERNAME);
        when(this.userService.loadUserByUsername(USERNAME)).thenReturn(this.userDetails);
        when(this.tokenDao.findByTokenAndUserId(TOKEN, this.user.getId())).thenReturn(this.token);
        this.token.setIsExpired(true);
        this.token.setIsRevoked(true);

        assertThat(this.tokenService.isValid(BEARER_TOKEN), is(false));
    }

    @Test
    void givenValidTokenWhenIsValidThenReturnTrue() {
        when(this.jwtParser.extractExpiration(TOKEN)).thenReturn(this.nonExpiredDate);
        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(USERNAME);
        when(this.userService.loadUserByUsername(USERNAME)).thenReturn(this.userDetails);
        when(this.tokenDao.findByTokenAndUserId(TOKEN, this.user.getId())).thenReturn(this.token);

        assertThat(this.tokenService.isValid(BEARER_TOKEN), is(true));
    }

    @Test
    void givenTokenWhenGetAuthenticationThenReturnUsernameAuthenticationToken() {
        String username = this.user.getUsername();

        when(this.jwtParser.extractUsername(TOKEN)).thenReturn(username);
        when(this.userService.loadUserByUsername(username)).thenReturn(this.userDetails);

        assertThat(this.tokenService.getAuthentication(BEARER_TOKEN).getName(), is(username));
    }

    @Test
    void givenExistentTokenWhenRevokeTokenThenRevokeToken() {
        Token tokenToRevoke = mock(Token.class);
        when(this.tokenDao.findByToken(TOKEN)).thenReturn(tokenToRevoke);
        doNothing().when(this.tokenDao).saveAll(List.of(tokenToRevoke));

        this.tokenService.revokeToken(TOKEN);

        verify(tokenToRevoke, times(1)).setIsRevoked(true);
        verify(tokenToRevoke, times(1)).setIsExpired(true);
        verify(this.tokenDao, times(1)).saveAll(List.of(tokenToRevoke));
    }

    @Test
    void givenNonExistentTokenWhenRevokeTokenThenDoNothing() {
        when(this.tokenDao.findByToken(TOKEN)).thenReturn(null);

        this.tokenService.revokeToken(TOKEN);

        verify(this.tokenDao, times(0)).saveAll(anyList());
    }

}
