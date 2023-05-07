package com.behabits.gymbo.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.domain.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final String EMAIL_CLAIM = "email";
    private static final String USERNAME_CLAIM = "username";

    private final UserServiceImpl userService;

    @Value("${gymbo.jwt.secret}")
    private String secret;
    @Value("${gymbo.jwt.issuer}")
    private String issuer;
    @Value("${gymbo.jwt.expire}")
    private int expire;

    @Override
    public String createToken(String username, String email) {
        return JWT.create()
                .withIssuer(this.issuer)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.expire * 1000L))
                .withClaim(USERNAME_CLAIM, username)
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC256(this.secret));
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = this.verify(token)
                .map(jwt -> jwt.getClaim(USERNAME_CLAIM).asString())
                .orElse(null);
        if (username == null) {
            return null;
        }
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) this.userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetailsImpl.getUsername(), token, Collections.emptyList());
    }

    private Optional<DecodedJWT> verify(String token) {
        try {
            return Optional.of(
                    JWT.require(Algorithm.HMAC256(this.secret))
                            .withIssuer(this.issuer).build()
                    .verify(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
