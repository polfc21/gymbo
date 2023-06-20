package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;

public interface TokenService {
    Token createToken(Token token);
    Boolean isValid(String token, User user);
}
