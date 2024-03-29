package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Review;
import com.behabits.gymbo.domain.repositories.ReviewModelRepository;
import com.behabits.gymbo.domain.services.ReviewService;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ReviewRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ReviewResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ReviewApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.ReviewRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.ReviewResponseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewApiMapper mapper;

    @MockBean
    private TokenService tokenService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final ReviewRequest reviewRequest = new ReviewRequestRepository().getReviewRequest();
    private final Review review = new ReviewModelRepository().getReview();
    private final ReviewResponse reviewResponse = new ReviewResponseRepository().getReview();

    @Test
    @WithMockUser
    void givenCorrectReviewRequestWithExistentUsernameAndDifferentUsernameWhenCreateReviewThenReturnReviewResponse() throws Exception {
        given(this.mapper.toDomain(this.reviewRequest)).willReturn(this.review);
        given(this.reviewService.createReview(this.review, this.reviewRequest.getUsernameReviewed())).willReturn(this.review);
        given(this.mapper.toResponse(this.review)).willReturn(this.reviewResponse);

        MockHttpServletResponse response = mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.REVIEWS)
                        .with(csrf())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.reviewRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser
    void givenIncorrectReviewRequestWhenCreateReviewThenReturnBadRequest() throws Exception {
        ReviewRequest incorrectReviewRequest = new ReviewRequestRepository().incorrectReviewRequest();

        MockHttpServletResponse response = mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.REVIEWS)
                        .with(csrf())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(incorrectReviewRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenCreateReviewThenReturn403() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.REVIEWS)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.reviewRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenExistentIdWhenFindReviewByIdThenReturnReviewResponse() throws Exception {
        Long existentId = 1L;

        given(this.reviewService.findReviewById(existentId)).willReturn(this.review);
        given(this.mapper.toResponse(this.review)).willReturn(this.reviewResponse);

        MockHttpServletResponse response = mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, this.review.getId())
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.reviewResponse)));
    }

    @Test
    @WithMockUser
    void givenNonExistentIdWhenFindReviewByIdThenReturn404() throws Exception {
        Long nonExistentId = 2L;

        given(this.reviewService.findReviewById(nonExistentId)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, nonExistentId)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    void givenUserHasNotPermissionsWhenFindReviewByIdThenReturn403() throws Exception {
        Long existentId = 1L;

        given(this.reviewService.findReviewById(existentId)).willThrow(PermissionsException.class);

        MockHttpServletResponse response = mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, existentId)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenExistentUsernameWhenFindAllReviewsByUsernameThenReturnReviewResponses() throws Exception {
        String existentUsername = "existentUsername";

        given(this.reviewService.findAllReviewsByUsername(existentUsername)).willReturn(List.of(this.review));
        given(this.mapper.toResponse(this.review)).willReturn(this.reviewResponse);

        MockHttpServletResponse response = mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.REVIEWED + ApiConstant.USERNAME, existentUsername)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(List.of(this.reviewResponse))));
    }

    @Test
    @WithMockUser
    void givenNonExistentUsernameWhenFindAllReviewsByUsernameThenReturn404() throws Exception {
        String nonExistentUsername = "nonExistentUsername";

        given(this.reviewService.findAllReviewsByUsername(nonExistentUsername)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.REVIEWED + ApiConstant.USERNAME, nonExistentUsername)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenFindAllReviewsByUsernameThenReturn403() throws Exception {
        String existentUsername = "existentUsername";

        MockHttpServletResponse response = mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.REVIEWED + ApiConstant.USERNAME, existentUsername))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @WithMockUser
    void givenExistentReviewWhenUpdateReviewThenReturnReviewResponse() throws Exception {
        Long existentId = 1L;

        given(this.mapper.toDomain(this.reviewRequest)).willReturn(this.review);
        given(this.reviewService.updateReview(existentId, this.review)).willReturn(this.review);
        given(this.mapper.toResponse(this.review)).willReturn(this.reviewResponse);

        MockHttpServletResponse response = mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, existentId)
                        .with(csrf())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.reviewRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenUpdateReviewThenReturn403() throws Exception {
        Long existentId = 1L;

        MockHttpServletResponse response = mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, existentId)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.reviewRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenExistentReviewWithPermissionsWhenDeleteReviewThenReturn204() throws Exception {
        Long existentId = 1L;

        MockHttpServletResponse response = mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, existentId)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    @WithMockUser
    void givenExistentReviewWithoutPermissionsWhenDeleteReviewThenReturn403() throws Exception {
        Long existentId = 1L;

        doThrow(PermissionsException.class).when(this.reviewService).deleteReview(existentId);

        MockHttpServletResponse response = mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, existentId)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenNonExistentReviewWhenDeleteReviewThenReturn404() throws Exception {
        Long nonExistentId = 2L;

        doThrow(NotFoundException.class).when(this.reviewService).deleteReview(nonExistentId);

        MockHttpServletResponse response = mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, nonExistentId)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenDeleteReviewThenReturn403() throws Exception {
        Long existentId = 1L;

        MockHttpServletResponse response = mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.REVIEWS + ApiConstant.ID, existentId))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

}
