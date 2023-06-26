package com.behabits.gymbo.application.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class JwtParserTest {

    @Autowired
    private JwtParser jwtParser;

    @Mock
    private JwtKeyManager jwtKeyManager;

    @Value("${gymbo.jwt.secret}")
    private String secret;

    private final String token = "token";

    private static MockedStatic<Jwts> jwtsMockedStatic;

    @BeforeAll
    static void setUp() {
        jwtsMockedStatic = mockStatic(Jwts.class);
    }

    @AfterAll
    static void tearDown() {
        jwtsMockedStatic.close();
    }


    @Test
    void givenTokenWhenExtractUsernameThenReturnUsername() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        JwtParserBuilder jwtParserBuilder = mock(JwtParserBuilder.class);
        io.jsonwebtoken.JwtParser jwtParser = mock(io.jsonwebtoken.JwtParser.class);
        String expectedUsername = "username";
        Jws<Claims> jwsClaims = mock(Jws.class);
        Claims claims = mock(Claims.class);

        when(Jwts.parserBuilder()).thenReturn(jwtParserBuilder);
        when(this.jwtKeyManager.getKey()).thenReturn(key);
        when(jwtParserBuilder.setSigningKey(key)).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(this.token)).thenReturn(jwsClaims);
        when(jwsClaims.getBody()).thenReturn(claims);
        when(claims.getSubject()).thenReturn(expectedUsername);

        assertThat(this.jwtParser.extractUsername(this.token), is(expectedUsername));
    }

    @Test
    void givenTokenWhenExtractExpirationThenReturnExpiration() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        JwtParserBuilder jwtParserBuilder = mock(JwtParserBuilder.class);
        io.jsonwebtoken.JwtParser jwtParser = mock(io.jsonwebtoken.JwtParser.class);
        Date expectedExpiration = new Date();
        Jws<Claims> jwsClaims = mock(Jws.class);
        Claims claims = mock(Claims.class);

        when(Jwts.parserBuilder()).thenReturn(jwtParserBuilder);
        when(this.jwtKeyManager.getKey()).thenReturn(key);
        when(jwtParserBuilder.setSigningKey(key)).thenReturn(jwtParserBuilder);
        when(jwtParserBuilder.build()).thenReturn(jwtParser);
        when(jwtParser.parseClaimsJws(this.token)).thenReturn(jwsClaims);
        when(jwsClaims.getBody()).thenReturn(claims);
        when(claims.getExpiration()).thenReturn(expectedExpiration);

        assertThat(this.jwtParser.extractExpiration(this.token), is(expectedExpiration));
    }
}
