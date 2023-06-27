package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.jwt.JwtBuilder;
import com.behabits.gymbo.domain.daos.TokenDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.TokenModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenDao tokenDao;

    @Mock
    private JwtBuilder jwtBuilder;

    private final Token token = new TokenModelRepository().getToken();
    private final User user = new UserModelRepository().getUser();

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
    void givenTokenAndUserArePresentAndTokenIsNotExpiredAndNotRevokedWhenIsValidThenReturnTrue() {
        when(this.tokenDao.findByTokenAndUserId(this.token.getToken(), this.user.getId())).thenReturn(this.token);
        this.token.setIsExpired(false);
        this.token.setIsRevoked(false);

        assertThat(this.tokenService.isValid(this.token.getToken(), this.user), is(true));
    }

    @Test
    void givenTokenAndUserArePresentAndTokenIsExpiredAndNotRevokedWhenIsValidThenReturnFalse() {
        when(this.tokenDao.findByTokenAndUserId(this.token.getToken(), this.user.getId())).thenReturn(this.token);
        this.token.setIsExpired(true);
        this.token.setIsRevoked(false);

        assertThat(this.tokenService.isValid(this.token.getToken(), this.user), is(false));
    }

    @Test
    void givenTokenAndUserArePresentAndTokenIsNotExpiredAndRevokedWhenIsValidThenReturnFalse() {
        when(this.tokenDao.findByTokenAndUserId(this.token.getToken(), this.user.getId())).thenReturn(this.token);
        this.token.setIsExpired(false);
        this.token.setIsRevoked(true);

        assertThat(this.tokenService.isValid(this.token.getToken(), this.user), is(false));
    }

    @Test
    void givenTokenAndUserArePresentAndTokenIsExpiredAndRevokedWhenIsValidThenReturnFalse() {
        when(this.tokenDao.findByTokenAndUserId(this.token.getToken(), this.user.getId())).thenReturn(this.token);
        this.token.setIsExpired(true);
        this.token.setIsRevoked(true);

        assertThat(this.tokenService.isValid(this.token.getToken(), this.user), is(false));
    }

    @Test
    void givenTokenAndUserAreNotPresentWhenIsValidThenThrowPermissionsException() {
        when(this.tokenDao.findByTokenAndUserId(this.token.getToken(), this.user.getId())).thenThrow(PermissionsException.class);

        assertThrows(PermissionsException.class, () -> this.tokenService.isValid(this.token.getToken(), this.user));
    }

}
