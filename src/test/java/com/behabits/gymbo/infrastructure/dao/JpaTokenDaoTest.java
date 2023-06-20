package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.repositories.TokenModelRepository;
import com.behabits.gymbo.infrastructure.repository.TokenRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.TokenEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.TokenEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaTokenDaoTest {

    @InjectMocks
    private JpaTokenDao tokenDao;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenEntityMapper mapper;

    private final Token token = new TokenModelRepository().getToken();
    private final TokenEntity tokenEntity = new TokenEntityRepository().getToken();

    @Test
    void givenUserIdWhenFindAllTokensByUserIdThenReturnTokenList() {
        Long userId = 1L;

        when(this.tokenRepository.findAllByPlayerId(userId)).thenReturn(List.of(this.tokenEntity));
        when(this.mapper.toDomain(this.tokenEntity)).thenReturn(this.token);

        assertThat(this.tokenDao.findAllTokensByUserId(userId), is(List.of(this.token)));
    }

    @Test
    void givenTokenListWhenSaveAllThenVerifySaveAll() {
        when(this.mapper.toEntity(this.token)).thenReturn(this.tokenEntity);
        this.tokenDao.saveAll(List.of(this.token));

        verify(this.tokenRepository).saveAll(List.of(this.tokenEntity));
    }

    @Test
    void givenTokenWhenCreateTokenThenReturnToken() {
        when(this.mapper.toEntity(this.token)).thenReturn(this.tokenEntity);
        when(this.tokenRepository.save(this.tokenEntity)).thenReturn(this.tokenEntity);
        when(this.mapper.toDomain(this.tokenEntity)).thenReturn(this.token);

        assertThat(this.tokenDao.createToken(this.token), is(this.token));
    }

    @Test
    void givenTokenAndUserIdAreAllPresentWhenFindByTokenAndUserIdThenReturnToken() {
        String presentToken = "presentToken";
        Long presentUserId = 1L;

        when(this.tokenRepository.findByTokenAndPlayerId(presentToken, presentUserId)).thenReturn(this.tokenEntity);
        when(this.mapper.toDomain(this.tokenEntity)).thenReturn(this.token);

        assertThat(this.tokenDao.findByTokenAndUserId(presentToken, presentUserId), is(this.token));
    }

    @Test
    void givenTokenAndUserIdAreNotPresentWhenFindByTokenAndUserIdThenThrowPermissionsException() {
        String notPresentToken = "notPresentToken";
        Long notPresentUserId = 1L;

        when(this.tokenRepository.findByTokenAndPlayerId(notPresentToken, notPresentUserId)).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.tokenDao.findByTokenAndUserId(notPresentToken, notPresentUserId));
    }
}
