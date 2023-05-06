package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.domain.services.UserService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.UserApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.UserRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.UserResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserApiMapper mapper;

    @MockBean
    private JwtService jwtService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final UserRequestRepository userRequestRepository = new UserRequestRepository();

    private final UserModelRepository userModelRepository = new UserModelRepository();

    private final UserResponseRepository userResponseRepository = new UserResponseRepository();

    @Test
    @WithMockUser
    void givenCorrectUserRequestWhenCreateUserThenReturnUserCreatedAnd201() throws Exception {
        UserRequest userRequest = this.userRequestRepository.getCorrectUserRequest();
        User user = this.userModelRepository.getUser();
        UserResponse userResponse = this.userResponseRepository.getUserResponse();
        given(this.mapper.toDomain(userRequest)).willReturn(user);
        given(this.userService.createUser(user)).willReturn(user);
        given(this.mapper.toResponse(user)).willReturn(userResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(userResponse)));
    }

    @Test
    @WithMockUser
    void givenIncorrectUserRequestWhenCreateUserThenReturn400() throws Exception {
        UserRequest incorrectUserRequest = this.userRequestRepository.getIncorrectUserRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectUserRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedWhenCreateUserThenReturn403() throws Exception {
        UserRequest incorrectUserRequest = this.userRequestRepository.getIncorrectUserRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectUserRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

}
