package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.infrastructure.controller.repositories.request.ExerciseRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.request.SerieRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.ExerciseResponseRepository;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ExerciseApiMapper;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ExerciseController.class)
class ExerciseControllerTest {

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private ExerciseApiMapper mapper;

    @MockBean
    private JwtService jwtService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final ExerciseRequest squatRequest = new ExerciseRequestRepository().getSquatExerciseRequest();

    private final ExerciseResponse squatResponse = new ExerciseResponseRepository().getSquatExerciseResponse();

    private final Exercise squatExercise = new ExerciseModelRepository().getSquatExercise();

    private final SerieRequestRepository serieRequestRepository = new SerieRequestRepository();

    @Test
    @WithMockUser
    void givenExistentIdWhenFindExerciseByIdThenReturnExerciseResponseAnd200() throws Exception {
        given(this.exerciseService.findExerciseById(1L)).willReturn(squatExercise);
        given(this.mapper.toResponse(squatExercise)).willReturn(squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

    @Test
    @WithMockUser
    void givenNotExistentIdWhenFindExerciseByIdThenReturn404() throws Exception {
        Long notExistentId = 1L;

        given(this.exerciseService.findExerciseById(notExistentId)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, notExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser
    void givenLoggedUserHasNotPermissionsWhenFindExerciseByIdThenReturn403() throws Exception {
        Long notPermissionsId = 1L;

        given(this.exerciseService.findExerciseById(notPermissionsId)).willThrow(PermissionsException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, notPermissionsId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenSquatExerciseWithSeriesWhenCreateExerciseThenReturnSquatExerciseResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.createExercise(this.squatExercise)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatResponse)));
    }

    @WithMockUser
    @Test
    void givenSquatExerciseRequestWhenCreateExerciseThenReturnSquatExerciseResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.createExercise(this.squatExercise)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatResponse)));
    }

    @WithMockUser
    @Test
    void givenNullSerieListRequestWhenCreateExerciseThenReturn201() throws Exception {
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.createExercise(this.squatExercise)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatResponse)));
    }

    @WithMockUser
    @Test
    void givenNullSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        SerieRequest nullSerieRequest = this.serieRequestRepository.getNullSerieRequest();
        this.squatRequest.setSeries(List.of(nullSerieRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @WithMockUser
    @Test
    void givenIncorrectSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        SerieRequest incorrectSerieRequest = this.serieRequestRepository.getIncorrectSerieRequest();
        this.squatRequest.setSeries(List.of(incorrectSerieRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @WithMockUser
    @Test
    void givenIncorrectExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        ExerciseRequest incorrectRequest = new ExerciseRequestRepository().getIncorrectExerciseRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @WithMockUser
    @Test
    void givenNullExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        ExerciseRequest nullRequest = new ExerciseRequestRepository().getNullExerciseRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(nullRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedWhenCreateExerciseThenReturn403() throws Exception {
        ExerciseRequest nullRequest = new ExerciseRequestRepository().getNullExerciseRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(nullRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenTrainingIdWhenFindExercisesByTrainingIdThenReturn200() throws Exception {
        long trainingId = 1L;
        List<Exercise> squats = List.of(this.squatExercise);
        List<ExerciseResponse> squatResponses = List.of(this.squatResponse);
        given(this.exerciseService.findExercisesByTrainingId(1L)).willReturn(squats);
        given(this.mapper.toResponse(squats)).willReturn(squatResponses);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .param("trainingId", Long.toString(trainingId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponses)));
    }

    @WithMockUser
    @Test
    void givenNullTrainingIdWhenFindExercisesByTrainingIdThenReturn200() throws Exception {
        List<Exercise> squats = List.of(this.squatExercise);
        List<ExerciseResponse> squatResponses = List.of(this.squatResponse);
        given(this.exerciseService.findExercisesByTrainingId(null)).willReturn(squats);
        given(this.mapper.toResponse(squats)).willReturn(squatResponses);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponses)));
    }

    @Test
    @WithMockUser
    void givenLoggedUserHasNotPermissionsWhenDeleteExerciseThenReturn403() throws Exception {
        long exerciseId = 1L;
        doThrow(PermissionsException.class).when(this.exerciseService).deleteExercise(exerciseId);

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenNonExistentIdWhenDeleteExerciseThenReturn404() throws Exception {
        long exerciseId = 1L;
        doThrow(NotFoundException.class).when(this.exerciseService).deleteExercise(exerciseId);

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @WithMockUser
    @Test
    void givenExistentIdWhenDeleteExerciseThenReturn204() throws Exception {
        long id = 1L;
        doNothing().when(this.exerciseService).deleteExercise(id);

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
        assertThat(response.getContentAsString(), is("Exercise with id " + id + " deleted successfully"));
    }

    @Test
    void givenNonAuthenticatedWhenDeleteExerciseThenReturn403() throws Exception {
        long id = 1L;

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenNonExistentIdWhenUpdateExerciseThenReturn404() throws Exception {
        Long nonExistentId = 1L;
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.updateExercise(nonExistentId, this.squatExercise)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @WithMockUser
    @Test
    void givenLoggedUserHasNotPermissionsWhenUpdateExerciseThenReturn403() throws Exception {
        Long notPermissionsId = 1L;
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.updateExercise(notPermissionsId, this.squatExercise)).willThrow(PermissionsException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, notPermissionsId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser
    @Test
    void givenExistentIdWhenUpdateExerciseThenReturn200() throws Exception {
        Long id = 1L;
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.updateExercise(id, this.squatExercise)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.squatResponse)));
    }

    @WithMockUser
    @Test
    void givenIncorrectExerciseWhenUpdateExerciseThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(new ExerciseRequest()))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNonAuthenticatedWhenUpdateExerciseThenReturn403() throws Exception {
        Long id = 1L;
        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.squatRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }
}
