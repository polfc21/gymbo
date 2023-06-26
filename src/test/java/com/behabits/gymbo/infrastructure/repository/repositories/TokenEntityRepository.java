package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.TokenEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenEntityRepository {

    public TokenEntity getToken() {
        return TokenEntity.builder()
                .id(1L)
                .token("token")
                .revoked(false)
                .expired(false)
                .build();
    }
}
