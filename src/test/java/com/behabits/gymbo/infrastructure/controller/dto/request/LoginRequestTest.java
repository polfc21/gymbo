package com.behabits.gymbo.infrastructure.controller.dto.request;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LoginRequestTest {

    @Test
    void givenSameLoginRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        LoginRequest loginRequest2 = new LoginRequest("username", "password");
        assertThat(loginRequest, is(loginRequest2));
        assertThat(loginRequest.hashCode(), is(loginRequest2.hashCode()));
    }
}
