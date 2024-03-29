package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.services.SerieService;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.SerieApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.SerieRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.SerieResponseRepository;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(SerieController.class)
class SerieControllerTest {

    @MockBean
    private SerieService serieService;

    @MockBean
    private SerieApiMapper mapper;

    @MockBean
    private TokenService tokenService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Serie squatSerie = new SerieModelRepository().getSquatSerie();
    private final SerieResponse squatSerieResponse = new SerieResponseRepository().getSquatSerieResponse();
    private final SerieRequest squatSerieRequest = new SerieRequestRepository().getSquatSerieRequest();

    @WithMockUser
    @Test
    void givenLoggedUserHasNotPermissionsWhenFindSerieByIdThenReturn403() throws Exception {
        doThrow(PermissionsException.class).when(this.serieService).findSerieById(this.squatSerie.getId());

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenLoggedUserHasPermissionsWhenFindSerieByIdThenReturn200() throws Exception {
        when(this.serieService.findSerieById(this.squatSerie.getId())).thenReturn(this.squatSerie);
        when(this.mapper.toResponse(this.squatSerie)).thenReturn(this.squatSerieResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatSerieResponse)));
    }

    @WithMockUser
    @Test
    void givenNonExistentSerieWhenFindSerieByIdThenReturn404() throws Exception {
        doThrow(NotFoundException.class).when(this.serieService).findSerieById(this.squatSerie.getId());

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @WithMockUser
    @Test
    void givenLoggedUserHasNotPermissionsWhenDeleteSerieThenReturn403() throws Exception {
        doThrow(PermissionsException.class).when(this.serieService).deleteSerie(this.squatSerie.getId());

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenLoggedUserHasPermissionsWhenDeleteSerieThenReturn204() throws Exception {
        doNothing().when(this.serieService).deleteSerie(this.squatSerie.getId());

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @WithMockUser
    @Test
    void givenNonExistentSerieWhenDeleteSerieThenReturn404() throws Exception {
        doThrow(NotFoundException.class).when(this.serieService).deleteSerie(this.squatSerie.getId());

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenDeleteSerieThenReturn403() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenLoggedUserHasNotPermissionsWhenUpdateSerieThenReturn403() throws Exception {
        when(this.mapper.toDomain(this.squatSerieRequest)).thenReturn(this.squatSerie);
        doThrow(PermissionsException.class).when(this.serieService).updateSerie(this.squatSerie.getId(), this.squatSerie);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenLoggedUserHasPermissionsWhenUpdateSerieThenReturn200() throws Exception {
        when(this.mapper.toDomain(this.squatSerieRequest)).thenReturn(this.squatSerie);
        when(this.serieService.updateSerie(this.squatSerie.getId(), this.squatSerie)).thenReturn(this.squatSerie);
        when(this.mapper.toResponse(this.squatSerie)).thenReturn(this.squatSerieResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatSerieResponse)));
    }

    @WithMockUser
    @Test
    void givenNonExistentSerieWhenUpdateSerieThenReturn404() throws Exception {
        when(this.mapper.toDomain(this.squatSerieRequest)).thenReturn(this.squatSerie);
        doThrow(NotFoundException.class).when(this.serieService).updateSerie(this.squatSerie.getId(), this.squatSerie);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @WithMockUser
    @Test
    void givenIncorrectSerieRequestWhenUpdateSerieThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(new SerieRequest()))
                        .with(csrf()))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedUserWhenUpdateSerieThenReturn403() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                        put(ApiConstant.API_V1 + ApiConstant.SERIES + ApiConstant.ID, this.squatSerie.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(this.squatSerie)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }


    @WithMockUser
    @Test
    void givenNonExistentExerciseIdWhenFindSeriesByExerciseIdThenReturn404() throws Exception {
        long exerciseId = 1L;
        given(this.serieService.findSeriesByExerciseId(exerciseId)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    void givenLoggedUserHasNotPermissionsWhenFindSeriesByExerciseByIdThenReturn403() throws Exception {
        Long nonPermissionsId = 1L;

        given(this.serieService.findSeriesByExerciseId(nonPermissionsId)).willThrow(PermissionsException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, nonPermissionsId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenExistentExerciseIdWhenFindSeriesByExerciseIdThenReturn200() throws Exception {
        long exerciseId = 1L;
        given(this.serieService.findSeriesByExerciseId(exerciseId)).willReturn(List.of(this.squatSerie));
        given(this.mapper.toResponse(List.of(this.squatSerie))).willReturn(List.of(this.squatSerieResponse));

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(List.of(this.squatSerieResponse))));
    }

    @Test
    @WithMockUser
    void givenLoggedUserHasNotPermissionsWhenCreateSerieThenReturn403() throws Exception {
        Long notPermissionsId = 1L;
        given(this.mapper.toDomain(this.squatSerieRequest)).willReturn(this.squatSerie);
        given(this.serieService.createSerie(notPermissionsId, this.squatSerie)).willThrow(PermissionsException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, notPermissionsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenExistentExerciseIdWhenCreateSerieThenReturn201() throws Exception {
        long exerciseId = 1L;
        given(this.mapper.toDomain(this.squatSerieRequest)).willReturn(this.squatSerie);
        given(this.serieService.createSerie(exerciseId, this.squatSerie)).willReturn(this.squatSerie);
        given(this.mapper.toResponse(this.squatSerie)).willReturn(this.squatSerieResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatSerieResponse)));
    }

    @WithMockUser
    @Test
    void givenNonExistentExerciseIdWhenCreateSerieThenReturn404() throws Exception {
        long exerciseId = 1L;
        given(this.mapper.toDomain(this.squatSerieRequest)).willReturn(this.squatSerie);
        given(this.serieService.createSerie(exerciseId, this.squatSerie)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedWhenCreateSerieThenReturn403() throws Exception {
        long exerciseId = 1L;

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatSerieRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

}
