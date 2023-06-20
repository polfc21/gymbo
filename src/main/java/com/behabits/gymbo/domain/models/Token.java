package com.behabits.gymbo.domain.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private Long id;
    private String token;
    private Boolean isRevoked;
    private Boolean isExpired;
    private User user;

    public Token(String token) {
        this.token = token;
        this.isExpired = false;
        this.isRevoked = false;
    }

}
