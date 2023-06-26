package com.behabits.gymbo.application.jwt;

import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class JwtBuilderTest {

    @Autowired
    private JwtBuilder jwtBuilder;

    @Mock
    private JwtKeyManager jwtKeyManager;

    @Value("${gymbo.jwt.secret}")
    private String secret;

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
    void whenBuildTokenThenReturnToken() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        io.jsonwebtoken.JwtBuilder jwtBuilder = mock(io.jsonwebtoken.JwtBuilder.class);
        String expectedToken = "token";
        UserDetailsImpl userDetails = new UserDetailsImpl(new UserModelRepository().getUser());

        when(Jwts.builder()).thenReturn(jwtBuilder);
        when(jwtBuilder.setClaims(new HashMap<>())).thenReturn(jwtBuilder);
        when(jwtBuilder.setSubject(any())).thenReturn(jwtBuilder);
        when(jwtBuilder.setIssuer(any())).thenReturn(jwtBuilder);
        when(jwtBuilder.setIssuedAt(any())).thenReturn(jwtBuilder);
        when(jwtBuilder.setExpiration(any())).thenReturn(jwtBuilder);
        Mockito.when(this.jwtKeyManager.getKey()).thenReturn(key);
        when(jwtBuilder.signWith(key, io.jsonwebtoken.SignatureAlgorithm.HS256)).thenReturn(jwtBuilder);
        when(jwtBuilder.compact()).thenReturn(expectedToken);

        assertThat(this.jwtBuilder.buildToken(userDetails), is(expectedToken));
    }
}
