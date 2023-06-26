package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.domain.models.Token;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String token;

    public TokenResponse(Token token) {
        this.token = token.getToken();
    }

}
