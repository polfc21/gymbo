package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Token;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthenticationService;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.domain.services.UserService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.LoginRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.UserRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TokenResponse;
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
import org.springframework.security.authentication.BadCredentialsException;
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
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void givenCorrectUserRequestWhenCreateUserThenReturnUserCreatedAnd201() throws Exception {
        UserRequest userRequest = new UserRequestRepository().getCorrectUserRequest();
        User user = new UserModelRepository().getUser();
        UserResponse userResponse = new UserResponseRepository().getUserResponse();
        given(this.mapper.toDomain(userRequest)).willReturn(user);
        given(this.userService.createUser(user)).willReturn(user);
        given(this.mapper.toResponse(user)).willReturn(userResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.AUTH + ApiConstant.REGISTER)
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
        UserRequest incorrectUserRequest = new UserRequestRepository().getIncorrectUserRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.AUTH + ApiConstant.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectUserRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedWhenCreateUserThenReturn403() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.AUTH + ApiConstant.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(null))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenValidLoginRequestWhenLoginThenReturnTokenAnd200() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        Token token = new Token("dummyToken");
        TokenResponse tokenResponse = new TokenResponse(token);

        given(this.authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword())).willReturn(token);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.AUTH + ApiConstant.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(loginRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(tokenResponse)));
    }


    @Test
    @WithMockUser
    void givenInvalidLoginRequestWhenLoginThenReturn403() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("incorrect_password");

        given(this.authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword())).willThrow(BadCredentialsException.class);

        MockHttpServletResponse response = mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.AUTH + ApiConstant.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(loginRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }


}
