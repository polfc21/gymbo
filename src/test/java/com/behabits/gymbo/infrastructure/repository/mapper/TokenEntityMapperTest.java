package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.repositories.TokenModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.TokenEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TokenEntityMapperTest {

    @Autowired
    private TokenEntityMapper mapper;

    private final TokenEntityRepository tokenEntityRepository = new TokenEntityRepository();

    private final TokenModelRepository tokenModelRepository = new TokenModelRepository();

    @Test
    void givenTokenWhenMapToEntityThenReturnTokenEntity() {
        Token token = this.tokenModelRepository.getToken();

        TokenEntity tokenEntity = this.mapper.toEntity(token);

        assertThat(tokenEntity.getId(), is(token.getId()));
        assertThat(tokenEntity.getToken(), is(token.getToken()));
        assertThat(tokenEntity.getExpired(), is(token.getIsExpired()));
        assertThat(tokenEntity.getRevoked(), is(token.getIsRevoked()));
    }

    @Test
    void givenTokenEntityWhenMapToDomainThenReturnToken() {
        TokenEntity tokenEntity = this.tokenEntityRepository.getToken();

        Token token = this.mapper.toDomain(tokenEntity);

        assertThat(token.getId(), is(tokenEntity.getId()));
        assertThat(token.getToken(), is(tokenEntity.getToken()));
        assertThat(token.getIsExpired(), is(tokenEntity.getExpired()));
        assertThat(token.getIsRevoked(), is(tokenEntity.getRevoked()));
    }
}
