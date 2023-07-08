package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Token;

import java.util.List;

public interface TokenDao {
    Token findByToken(String token);
    List<Token> findAllTokensByUserId(Long userId);
    void saveAll(List<Token> tokens);
    Token createToken(Token token);
    Token findByTokenAndUserId(String token, Long userId);
}
