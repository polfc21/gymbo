package com.behabits.gymbo.application.jwt;

import com.behabits.gymbo.domain.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JwtBuilder {

    private final JwtKeyManager jwtKeyManager;

    @Value("${gymbo.jwt.issuer}")
    private String issuer;
    @Value("${gymbo.jwt.expire}")
    private int expire;

    public String buildToken(User user) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuer(this.issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.expire))
                .signWith(this.jwtKeyManager.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
