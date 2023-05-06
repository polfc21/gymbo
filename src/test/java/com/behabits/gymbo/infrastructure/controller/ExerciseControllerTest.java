package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.domain.services.JwtService;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.SerieApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.ExerciseRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.request.SerieRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.ExerciseResponseRepository;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ExerciseApiMapper;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@WebMvcTest(ExerciseController.class)
class ExerciseControllerTest {

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private ExerciseApiMapper mapper;

    @MockBean
    private SerieApiMapper serieMapper;

    @MockBean
    private JwtService jwtService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();

    private final ExerciseResponseRepository exerciseResponseRepository = new ExerciseResponseRepository();

    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    private final SerieRequestRepository serieRequestRepository = new SerieRequestRepository();

    private final SerieResponseRepository serieResponseRepository = new SerieResponseRepository();

    private final SerieModelRepository serieModelRepository = new SerieModelRepository();

    @Test
    @WithMockUser
    void givenExistentIdWhenFindExerciseByIdThenReturnExerciseResponseAnd200() throws Exception {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExercise();
        ExerciseResponse squatResponse = this.exerciseResponseRepository.getSquatExerciseResponse();
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
        given(this.exerciseService.findExerciseById(1L)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @WithMockUser
    @Test
    void givenSquatExerciseWithSeriesWhenCreateExerciseThenReturnSquatExerciseResponseAnd201() throws Exception {
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequestWithSeries();
        Exercise squatExercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        ExerciseResponse squatResponse = this.exerciseResponseRepository.getSquatExerciseResponseWithSeries();
        given(this.mapper.toDomain(squatRequest)).willReturn(squatExercise);
        given(this.exerciseService.createExercise(squatExercise)).willReturn(squatExercise);
        given(this.mapper.toResponse(squatExercise)).willReturn(squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

    @WithMockUser
    @Test
    void givenSquatExerciseRequestWhenCreateExerciseThenReturnSquatExerciseResponseAnd201() throws Exception {
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequest();
        Exercise squatExercise = this.exerciseModelRepository.getSquatExercise();
        ExerciseResponse squatResponse = this.exerciseResponseRepository.getSquatExerciseResponse();
        given(this.mapper.toDomain(squatRequest)).willReturn(squatExercise);
        given(this.exerciseService.createExercise(squatExercise)).willReturn(squatExercise);
        given(this.mapper.toResponse(squatExercise)).willReturn(squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

    @WithMockUser
    @Test
    void givenNullSerieListRequestWhenCreateExerciseThenReturn201() throws Exception {
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequest();
        squatRequest.setSeries(List.of());
        ExerciseResponse squatResponse = this.exerciseResponseRepository.getSquatExerciseResponse();
        squatResponse.setSeries(List.of());
        Exercise squatExercise = this.exerciseModelRepository.getSquatExercise();
        given(this.mapper.toDomain(squatRequest)).willReturn(squatExercise);
        given(this.exerciseService.createExercise(squatExercise)).willReturn(squatExercise);
        given(this.mapper.toResponse(squatExercise)).willReturn(squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

    @WithMockUser
    @Test
    void givenNullSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        SerieRequest nullSerieRequest = this.serieRequestRepository.getNullSerieRequest();
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequest();
        squatRequest.setSeries(List.of(nullSerieRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @WithMockUser
    @Test
    void givenIncorrectSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        SerieRequest incorrectSerieRequest = this.serieRequestRepository.getIncorrectSerieRequest();
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequest();
        squatRequest.setSeries(List.of(incorrectSerieRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @WithMockUser
    @Test
    void givenIncorrectExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        ExerciseRequest incorrectRequest = this.exerciseRequestRepository.getIncorrectExerciseRequest();

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
        ExerciseRequest nullRequest = this.exerciseRequestRepository.getNullExerciseRequest();

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
        ExerciseRequest nullRequest = this.exerciseRequestRepository.getNullExerciseRequest();

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
        Exercise exercise = this.exerciseModelRepository.getSquatExercise();
        List<Exercise> exercises = List.of(exercise);
        ExerciseResponse exerciseResponse = this.exerciseResponseRepository.getSquatExerciseResponse();
        List<ExerciseResponse> exerciseResponses = List.of(exerciseResponse);
        given(this.exerciseService.findExercisesByTrainingId(1L)).willReturn(exercises);
        given(this.mapper.toResponse(exercises)).willReturn(exerciseResponses);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .param("trainingId", Long.toString(trainingId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(exerciseResponses)));
    }

    @WithMockUser
    @Test
    void givenNullTrainingIdWhenFindExercisesByTrainingIdThenReturn200() throws Exception {
        Exercise exercise = this.exerciseModelRepository.getSquatExercise();
        List<Exercise> exercises = List.of(exercise);
        ExerciseResponse exerciseResponse = this.exerciseResponseRepository.getSquatExerciseResponse();
        List<ExerciseResponse> exerciseResponses = List.of(exerciseResponse);
        given(this.exerciseService.findExercisesByTrainingId(null)).willReturn(exercises);
        given(this.mapper.toResponse(exercises)).willReturn(exerciseResponses);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(exerciseResponses)));
    }

    @WithMockUser
    @Test
    void givenNonExistentExerciseIdWhenFindSeriesByExerciseIdThenReturn404() throws Exception {
        long exerciseId = 1L;
        given(this.exerciseService.findSeriesByExerciseId(exerciseId)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @WithMockUser
    @Test
    void givenExistentExerciseIdWhenFindSeriesByExerciseIdThenReturn200() throws Exception {
        long exerciseId = 1L;
        Serie serie = this.serieModelRepository.getSquatSerie();
        List<Serie> series = List.of(serie);
        SerieResponse serieResponse = this.serieResponseRepository.getSquatSerieResponse();
        List<SerieResponse> seriesResponse = List.of(serieResponse);
        given(this.exerciseService.findSeriesByExerciseId(exerciseId)).willReturn(series);
        given(this.serieMapper.toResponse(series)).willReturn(seriesResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(seriesResponse)));
    }

    @WithMockUser
    @Test
    void givenExistentExerciseIdWhenCreateSerieThenReturn201() throws Exception {
        long exerciseId = 1L;
        SerieRequest squatSerieRequest = this.serieRequestRepository.getSquatSerieRequest();
        Serie serie = this.serieModelRepository.getSquatSerie();
        SerieResponse squatSerieResponse = this.serieResponseRepository.getSquatSerieResponse();
        given(this.serieMapper.toDomain(squatSerieRequest)).willReturn(serie);
        given(this.exerciseService.createSerie(exerciseId, serie)).willReturn(serie);
        given(this.serieMapper.toResponse(serie)).willReturn(squatSerieResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatSerieRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatSerieResponse)));
    }

    @WithMockUser
    @Test
    void givenNonExistentExerciseIdWhenCreateSerieThenReturn404() throws Exception {
        long exerciseId = 1L;
        SerieRequest squatSerieRequest = this.serieRequestRepository.getSquatSerieRequest();
        Serie serie = this.serieModelRepository.getSquatSerie();
        given(this.serieMapper.toDomain(squatSerieRequest)).willReturn(serie);
        given(this.exerciseService.createSerie(exerciseId, serie)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatSerieRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenNonAuthenticatedWhenCreateSerieThenReturn403() throws Exception {
        long exerciseId = 1L;
        SerieRequest squatSerieRequest = this.serieRequestRepository.getSquatSerieRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES, exerciseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatSerieRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }
}
