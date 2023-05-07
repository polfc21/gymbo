package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.UserResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class UserResponseTest {

    private final UserResponseRepository userResponseRepository = new UserResponseRepository();

    @Test
    void givenSameUserResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        UserResponse userResponse = this.userResponseRepository.getUserResponse();
        UserResponse userResponse2 = this.userResponseRepository.getUserResponse();

        assertThat(userResponse, is(userResponse2));
        assertThat(userResponse.hashCode(), is(userResponse2.hashCode()));
    }
}
