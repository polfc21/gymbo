package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TokenDao;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenDao tokenDao;

    @Override
    public Token createToken(Token token) {
        this.revokeAllUserTokens(token.getUser());
        return this.tokenDao.createToken(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> userTokens = this.tokenDao.findAllTokensByUserId(user.getId());
        if (!userTokens.isEmpty()) {
            userTokens.forEach(token -> {
                token.setIsRevoked(true);
                token.setIsExpired(true);
            });
            this.tokenDao.saveAll(userTokens);
        }
    }

    @Override
    public Boolean isValid(String bearerToken, User user) {
        Token token = this.tokenDao.findByTokenAndUserId(bearerToken, user.getId());
        return !token.getIsExpired() && !token.getIsRevoked();
    }

}
