package com.behabits.gymbo.infrastructure.controller.mapper;


import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import com.behabits.gymbo.infrastructure.controller.repositories.request.UserRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserApiMapperTest {

    @Autowired
    private UserApiMapper userApiMapper;

    private final UserRequestRepository userRequestRepository = new UserRequestRepository();

    private final UserModelRepository userModelRepository = new UserModelRepository();

    @Test
    void givenUserRequestWhenMapToDomainThenReturnUser() {
        UserRequest userRequest = this.userRequestRepository.getCorrectUserRequest();

        User user = this.userApiMapper.toDomain(userRequest);

        assertThat(user.getUsername(), is(userRequest.getUsername()));
        assertThat(user.getPassword(), startsWith("$2a$"));
        assertTrue(BCrypt.checkpw(userRequest.getPassword(), user.getPassword()));
        assertThat(user.getEmail(), is(userRequest.getEmail()));
        assertThat(user.getFirstName(), is(userRequest.getFirstName()));
        assertThat(user.getLastName(), is(userRequest.getLastName()));
    }

    @Test
    void givenUserWhenMapToResponseThenReturnUserResponse() {
        User user = this.userModelRepository.getUser();

        UserResponse userResponse = this.userApiMapper.toResponse(user);

        assertThat(userResponse.getId(), is(user.getId()));
        assertThat(userResponse.getUsername(), is(user.getUsername()));
        assertThat(userResponse.getEmail(), is(user.getEmail()));
        assertThat(userResponse.getFirstName(), is(user.getFirstName()));
        assertThat(userResponse.getLastName(), is(user.getLastName()));
    }
}
