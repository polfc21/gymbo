package com.behabits.gymbo.infrastructure.controller;

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

import static org.mockito.BDDMockito.given;
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

}
