package com.behabits.gymbo.infrastructure.repository;

import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.TokenEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final UserEntity player = new UserEntityRepository().getUser();
    private final TokenEntity token = new TokenEntityRepository().getToken();

    @BeforeEach
    void setUp() {
        this.player.setId(null);
        this.entityManager.persist(this.player);
        this.token.setId(null);
        this.token.setPlayer(this.player);
        this.entityManager.persist(this.token);
    }

    @AfterEach
    void tearDown() {
        this.entityManager.remove(this.token);
        this.entityManager.remove(this.player);
    }

    @Test
    void givenTokenIsPresentWhenFindByTokenThenReturnToken() {
        TokenEntity token = this.tokenRepository.findByToken(this.token.getToken());

        assertThat(token, is(this.token));
    }

    @Test
    void givenTokenIsNotPresentWhenFindByTokenThenReturnNull() {
        TokenEntity token = this.tokenRepository.findByToken("");

        assertNull(token);
    }

    @Test
    void givenPlayerIdIsPresentWhenFindAllByPlayerIdThenReturnTokenList() {
        List<TokenEntity> tokens = this.tokenRepository.findAllByPlayerId(this.player.getId());

        assertThat(tokens, hasItem(this.token));
    }

    @Test
    void givenPlayerIdIsNotPresentWhenFindAllByPlayerIdThenReturnEmptyTokenList() {
        List<TokenEntity> tokens = this.tokenRepository.findAllByPlayerId(0L);

        assertThat(tokens.size(), is(0));
    }

    @Test
    void givenTokenAndUserIdAreAllPresentWhenFindByTokenAndPlayerIdThenReturnToken() {
        TokenEntity token = this.tokenRepository.findByTokenAndPlayerId(this.token.getToken(), this.player.getId());

        assertThat(token, is(this.token));
    }

    @Test
    void givenTokenIsNotPresentWhenFindByTokenAndPlayerIdThenReturnNull() {
        TokenEntity token = this.tokenRepository.findByTokenAndPlayerId("", this.player.getId());

        assertNull(token);
    }

    @Test
    void givenPlayerIdIsNotPresentWhenFindByTokenAndPlayerIdThenReturnNull() {
        TokenEntity token = this.tokenRepository.findByTokenAndPlayerId(this.token.getToken(), 0L);

        assertNull(token);
    }
}
