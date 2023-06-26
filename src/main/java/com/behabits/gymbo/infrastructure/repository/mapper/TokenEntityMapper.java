package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public Token toDomain(TokenEntity entity) {
        Token domain = new Token();
        domain.setId(entity.getId());
        domain.setToken(entity.getToken());
        domain.setIsExpired(entity.getExpired());
        domain.setIsRevoked(entity.getRevoked());
        domain.setUser(this.userEntityMapper.toDomain(entity.getPlayer()));
        return domain;
    }

    public TokenEntity toEntity(Token domain) {
        TokenEntity entity = new TokenEntity();
        entity.setId(domain.getId());
        entity.setToken(domain.getToken());
        entity.setExpired(domain.getIsExpired());
        entity.setRevoked(domain.getIsRevoked());
        entity.setPlayer(this.userEntityMapper.toEntity(domain.getUser()));
        return entity;
    }

}
