package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Token;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenModelRepository {

    public Token getToken() {
        return Token.builder()
                .id(1L)
                .token("token")
                .isExpired(false)
                .isRevoked(false)
                .build();
    }

}
