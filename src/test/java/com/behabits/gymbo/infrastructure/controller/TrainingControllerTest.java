package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.services.TrainingService;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.repositories.request.ExerciseRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.request.TrainingRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.TrainingResponseRepository;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.TrainingApiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@AutoConfigureJsonTesters
@WebMvcTest(TrainingController.class)
public class TrainingControllerTest {

    @MockBean
    private TrainingService trainingService;

    @MockBean
    private TrainingApiMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<List<TrainingResponse>> jsonTrainingsResponse;

    @Autowired
    private JacksonTester<TrainingResponse> jsonTrainingResponse;

    @Autowired
    private JacksonTester<TrainingRequest> jsonTrainingRequest;

    @Autowired
    private JacksonTester<Month> jsonMonth;

    private final TrainingRequestRepository trainingRequestRepository = new TrainingRequestRepository();

    private final TrainingResponseRepository trainingResponseRepository = new TrainingResponseRepository();

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();

    private final ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();

    @Test
    void givenMonthWhenFindTrainingsThenReturnTrainingsResponseAnd200() throws Exception {
        Training legTraining = this.trainingModelRepository.getLegTrainingWithSquatExercise();
        TrainingResponse legResponse = this.trainingResponseRepository.getLegTrainingResponseWithSquatExercise();
        given(this.trainingService.findTrainingsByMonth(Month.FEBRUARY)).willReturn(List.of(legTraining));
        given(this.mapper.toResponse(legTraining)).willReturn(legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonMonth.write(Month.FEBRUARY).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingsResponse.write(List.of(legResponse)).getJson()));
    }

    @Test
    void givenNullMonthWhenFindTrainingsThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenExistentIdWhenFindTrainingByIdThenReturnTrainingResponseAnd200() throws Exception {
        Training legTraining = this.trainingModelRepository.getLegTrainingWithSquatExercise();
        TrainingResponse legResponse = this.trainingResponseRepository.getLegTrainingResponseWithSquatExercise();
        given(this.trainingService.findTrainingById(1L)).willReturn(legTraining);
        given(this.mapper.toResponse(legTraining)).willReturn(legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingResponse.write(legResponse).getJson()));
    }

    @Test
    void givenNonExistentIdWhenFindTrainingByIdThenReturn404() throws Exception {
        given(this.trainingService.findTrainingById(1L)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenLegTrainingRequestWhenCreateTrainingThenReturnLegTrainingResponseAnd201() throws Exception {
        TrainingRequest legRequest = this.trainingRequestRepository.getLegTrainingRequestWithSquatExercise();
        Training legTraining = this.trainingModelRepository.getLegTrainingWithSquatExercise();
        TrainingResponse legResponse = this.trainingResponseRepository.getLegTrainingResponseWithSquatExercise();
        given(this.mapper.toDomain(legRequest)).willReturn(legTraining);
        given(this.trainingService.createTraining(legTraining)).willReturn(legTraining);
        given(this.mapper.toResponse(legTraining)).willReturn(legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(legRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingResponse.write(legResponse).getJson()));
    }

    @Test
    void givenNullExerciseListRequestWhenCreateTrainingThenReturn201() throws Exception {
        TrainingRequest legRequest = this.trainingRequestRepository.getLegTrainingRequest();
        Training legTraining = this.trainingModelRepository.getLegTraining();
        TrainingResponse legResponse = this.trainingResponseRepository.getLegTrainingResponse();
        given(this.mapper.toDomain(legRequest)).willReturn(legTraining);
        given(this.trainingService.createTraining(legTraining)).willReturn(legTraining);
        given(this.mapper.toResponse(legTraining)).willReturn(legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(legRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingResponse.write(legResponse).getJson()));
    }

    @Test
    void givenNullExerciseRequestWhenCreateTrainingThenReturn400() throws Exception {
        ExerciseRequest nullRequest = this.exerciseRequestRepository.getNullExerciseRequest();
        TrainingRequest legTrainingRequest = this.trainingRequestRepository.getLegTrainingRequest();
        legTrainingRequest.setExercises(List.of(nullRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(legTrainingRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenIncorrectExerciseRequestWhenCreateTrainingThenReturn400() throws Exception {
        ExerciseRequest incorrectRequest = this.exerciseRequestRepository.getIncorrectExerciseRequest();
        TrainingRequest legTrainingRequest = this.trainingRequestRepository.getLegTrainingRequest();
        legTrainingRequest.setExercises(List.of(incorrectRequest));

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(legTrainingRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenIncorrectTrainingRequestWhenCreateTrainingThenReturn400() throws Exception {
        TrainingRequest incorrectRequest = this.trainingRequestRepository.getIncorrectTrainingRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(incorrectRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenNullTrainingRequestWhenCreateTrainingThenReturn400() throws Exception {
        TrainingRequest nullRequest = this.trainingRequestRepository.getNullTrainingRequest();

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(nullRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }
}
