package com.behabits.gymbo.application.service;

import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.application.jwt.JwtBuilder;
import com.behabits.gymbo.application.jwt.JwtParser;
import com.behabits.gymbo.domain.daos.TokenDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenDao tokenDao;
    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;
    private final UserService userService;

    @Override
    public Token createToken(User user) {
        this.revokeAllUserTokens(user);
        String tokenString = this.jwtBuilder.buildToken(user);
        Token token = new Token(tokenString);
        token.setUser(user);
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
    public boolean isValid(String tokenString) {
        if (tokenString == null || !tokenString.startsWith("Bearer ")) {
            return false;
        }
        String bearerToken = tokenString.substring(7);
        Date expirationDate = this.jwtParser.extractExpiration(bearerToken);
        if (this.isExpired(expirationDate)) {
            return false;
        }
        String username = this.jwtParser.extractUsername(bearerToken);
        if (username == null) {
            return false;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) this.userService.loadUserByUsername(username);
        Token token = this.tokenDao.findByTokenAndUserId(bearerToken, userDetails.getUser().getId());
        if (token == null) {
            throw new PermissionsException("Token not found for this user");
        }
        return !token.getIsExpired() && !token.getIsRevoked();
    }

    private boolean isExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String bearerToken = token.substring(7);
        String username = this.jwtParser.extractUsername(bearerToken);
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) this.userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetailsImpl.getUsername(), bearerToken, Collections.emptyList());
    }

    @Override
    public void revokeToken(String token) {
        Token savedToken = this.tokenDao.findByToken(token);
        if (savedToken == null) {
            return;
        }
        savedToken.setIsExpired(true);
        savedToken.setIsRevoked(true);
        this.tokenDao.saveAll(List.of(savedToken));
    }

}
