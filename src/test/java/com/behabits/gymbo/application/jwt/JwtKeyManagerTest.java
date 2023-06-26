package com.behabits.gymbo.application.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtKeyManagerTest {

    @Autowired
    private JwtKeyManager jwtKeyManager;

    @Mock
    private JwtDecoder jwtDecoder;

    @Value("${gymbo.jwt.secret}")
    private String secret;

    @Test
    void whenGetKeyThenReturnKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        when(this.jwtDecoder.getKeyBytes(this.secret)).thenReturn(keyBytes);

        assertThat(this.jwtKeyManager.getKey(), is(key));
    }

}
