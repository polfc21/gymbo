package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.services.ExerciseService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();

    private final ExerciseResponseRepository exerciseResponseRepository = new ExerciseResponseRepository();

    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    private final SerieRequestRepository serieRequestRepository = new SerieRequestRepository();

    @Test
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
    void givenNotExistentIdWhenFindExerciseByIdThenReturnExerciseResponseAnd200() throws Exception {
        given(this.exerciseService.findExerciseById(1L)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

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
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

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
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

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
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(squatResponse)));
    }

    @Test
    void givenNullSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        SerieRequest nullSerieRequest = this.serieRequestRepository.getNullSerieRequest();
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequest();
        squatRequest.setSeries(List.of(nullSerieRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenIncorrectSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        SerieRequest incorrectSerieRequest = this.serieRequestRepository.getIncorrectSerieRequest();
        ExerciseRequest squatRequest = this.exerciseRequestRepository.getSquatExerciseRequest();
        squatRequest.setSeries(List.of(incorrectSerieRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(squatRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenIncorrectExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        ExerciseRequest incorrectRequest = this.exerciseRequestRepository.getIncorrectExerciseRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(incorrectRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNullExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        ExerciseRequest nullRequest = this.exerciseRequestRepository.getNullExerciseRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(nullRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

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
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(exerciseResponses)));
    }

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
}
