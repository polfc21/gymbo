package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.UserRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class UserRequestTest {

    private final UserRequestRepository userRequestRepository = new UserRequestRepository();

    @Test
    void givenSameUserRequestWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        UserRequest userRequest = this.userRequestRepository.getCorrectUserRequest();
        UserRequest userRequest2 = this.userRequestRepository.getCorrectUserRequest();

        assertThat(userRequest, is(userRequest2));
        assertThat(userRequest.hashCode(), is(userRequest2.hashCode()));
    }

}
