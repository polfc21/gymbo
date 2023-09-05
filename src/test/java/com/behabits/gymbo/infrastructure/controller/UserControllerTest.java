package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.ExistingUserException;
import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.TokenService;
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

import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private TokenService tokenService; // Added to load application context

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
                post(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.REGISTER)
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
                post(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectUserRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @WithMockUser
    void givenExistentUsernameWhenCreateUserThenReturn409() throws Exception {
        UserRequest userRequest = new UserRequestRepository().getCorrectUserRequest();
        User user = new UserModelRepository().getUser();
        given(this.mapper.toDomain(userRequest)).willReturn(user);
        given(this.userService.createUser(user)).willThrow(ExistingUserException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CONFLICT.value()));
    }

    @Test
    void givenNonAuthenticatedWhenCreateUserThenReturn403() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(null))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenKilometersWhenFindUsersInKilometersOrderedByDistanceFromLoggedUserThenReturnUsersAnd200() throws Exception {
        Double kilometers = 10.0;
        User user = new UserModelRepository().getUser();
        UserResponse userResponse = new UserResponseRepository().getUserResponse();
        given(this.userService.findUsersInKilometersOrderedByDistanceFromLoggedUser(kilometers)).willReturn(List.of(user));
        given(this.mapper.toResponse(user)).willReturn(userResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.KILOMETERS, kilometers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(List.of(userResponse))));
    }

    @Test
    @WithMockUser
    void givenCorrectSportWhenFindUsersBySportThenReturnUsersAnd200() throws Exception {
        String sport = "football";
        User user = new UserModelRepository().getUser();
        UserResponse userResponse = new UserResponseRepository().getUserResponse();
        given(this.userService.findUsersBySport(Sport.FOOTBALL)).willReturn(List.of(user));
        given(this.mapper.toResponse(user)).willReturn(userResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.SPORTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sport", sport)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(List.of(userResponse))));
    }

    @Test
    @WithMockUser
    void givenIncorrectSportWhenFindUsersBySportThenReturn400() throws Exception {
        String sport = "incorrectSport";

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.SPORTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sport", sport)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    void givenNonAuthenticatedWhenFindUsersBySportThenReturn403() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.USERS + ApiConstant.SPORTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.UNAUTHORIZED.value()));
    }

}
