package com.behabits.gymbo.infrastructure.controller.dto.response;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class TokenResponseTest {

    @Test
    void givenSameTokenResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        TokenResponse tokenResponse = new TokenResponse("token");
        TokenResponse tokenResponse2 = new TokenResponse("token");

        assertThat(tokenResponse, is(tokenResponse2));
        assertThat(tokenResponse.hashCode(), is(tokenResponse2.hashCode()));
    }
}
