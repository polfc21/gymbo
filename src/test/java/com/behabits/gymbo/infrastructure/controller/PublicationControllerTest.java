package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.repositories.PublicationModelRepository;
import com.behabits.gymbo.domain.services.PublicationService;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.PublicationApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.PublicationRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.PublicationResponseRepository;
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
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PublicationController.class)
class PublicationControllerTest {

    @MockBean
    private PublicationService publicationService;

    @MockBean
    private PublicationApiMapper mapper;

    @MockBean
    private TokenService tokenService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final PublicationRequest publicationRequest = new PublicationRequestRepository().getPublicationRequest();

    private final Publication publication = new PublicationModelRepository().getPublication();

    private final PublicationResponse publicationResponse = new PublicationResponseRepository().getPublicationResponse();

    @Test
    @WithMockUser
    void givenPublicationRequestAndExistentFileIdsWithPermissionsWhenCreatePublicationThenReturnPublicationResponse() throws Exception {
        given(this.mapper.toDomain(this.publicationRequest)).willReturn(this.publication);
        given(this.publicationService.createPublication(this.publication, this.publicationRequest.getFiles())).willReturn(this.publication);
        given(this.mapper.toResponse(this.publication)).willReturn(this.publicationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(post(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
                .with(csrf())
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(this.publicationRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.publicationResponse)));
    }

    @Test
    @WithMockUser
    void givenPublicationWithLinksWhenCreatePublicationThenReturnPublicationResponse() throws Exception {
        PublicationRequest publicationRequestWithLinks = new PublicationRequestRepository().getPublicationRequestWithLinks();
        Publication publicationWithLinks = new PublicationModelRepository().getPublicationWithLink();
        PublicationResponse publicationResponseWithLinks = new PublicationResponseRepository().getPublicationResponseWithLinks();
        given(this.mapper.toDomain(publicationRequestWithLinks)).willReturn(publicationWithLinks);
        given(this.publicationService.createPublication(publicationWithLinks, publicationRequestWithLinks.getFiles())).willReturn(publicationWithLinks);
        given(this.mapper.toResponse(publicationWithLinks)).willReturn(publicationResponseWithLinks);

        MockHttpServletResponse response = this.mockMvc.perform(post(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
                .with(csrf())
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(publicationRequestWithLinks)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(publicationResponseWithLinks)));
    }

    @Test
    @WithMockUser
    void givenPublicationRequestAndExistentFileIdsWithoutPermissionsWhenCreatePublicationThenReturn403() throws Exception {
        given(this.mapper.toDomain(this.publicationRequest)).willReturn(this.publication);
        doThrow(PermissionsException.class).when(this.publicationService).createPublication(this.publication, this.publicationRequest.getFiles());

        MockHttpServletResponse response = this.mockMvc.perform(post(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
                .with(csrf())
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(this.publicationRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenPublicationRequestAndNonExistentFileIdsWhenCreatePublicationThenReturn404() throws Exception {
        given(this.mapper.toDomain(this.publicationRequest)).willReturn(this.publication);
        doThrow(NotFoundException.class).when(this.publicationService).createPublication(this.publication, this.publicationRequest.getFiles());

        MockHttpServletResponse response = this.mockMvc.perform(post(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
                .with(csrf())
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(this.publicationRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenCreatePublicationThenReturn403() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(post(ApiConstant.API_V1 + ApiConstant.PUBLICATIONS)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(this.publicationRequest)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }
}
