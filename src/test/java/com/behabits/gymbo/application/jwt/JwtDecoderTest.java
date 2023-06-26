package com.behabits.gymbo.application.jwt;

import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class JwtDecoderTest {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Value("${gymbo.jwt.secret}")
    private String secret;

    @Test
    void givenSecretWhenDecodeThenReturnKeyBytes() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);

        assertThat(this.jwtDecoder.getKeyBytes(this.secret), is(keyBytes));
    }

}
