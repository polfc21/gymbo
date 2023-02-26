package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.services.TrainingService;
import com.behabits.gymbo.infrastructure.controller.builder.request.TrainingRequestBuilder;
import com.behabits.gymbo.infrastructure.controller.builder.response.TrainingResponseBuilder;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.TrainingApiMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    private TrainingRequest legRequest;

    private TrainingResponse legResponse;

    private Training legTraining;

    private TrainingRequest incorrectRequest;

    private TrainingRequest nullRequest;

    @BeforeEach
    void setUp() {
        TrainingRequestBuilder trainingRequestBuilder = new TrainingRequestBuilder();
        TrainingResponseBuilder trainingResponseBuilder = new TrainingResponseBuilder();
        TrainingModelRepository trainingModelRepository = new TrainingModelRepository();
        this.legRequest = trainingRequestBuilder.buildLegTrainingRequest();
        this.legResponse = trainingResponseBuilder.buildLegTrainingResponse();
        this.legTraining = trainingModelRepository.buildLegTraining();
        this.incorrectRequest = trainingRequestBuilder.buildIncorrectTrainingRequest();
        this.nullRequest = trainingRequestBuilder.buildNullTrainingRequest();
    }

    @Test
    void givenMonthWhenFindTrainingsThenReturnTrainingsResponseAnd200() throws Exception {
        given(this.trainingService.findTrainingsByMonth(Month.FEBRUARY)).willReturn(List.of(this.legTraining));
        given(this.mapper.toResponse(this.legTraining)).willReturn(this.legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonMonth.write(Month.FEBRUARY).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingsResponse.write(List.of(this.legResponse)).getJson()));
    }

    @Test
    void givenNullMonthWhenFindTrainingsThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("null")
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.trainingService, times(0)).findTrainingsByMonth(null);
        verify(this.mapper, times(0)).toResponse(this.legTraining);
    }

    @Test
    void givenExistentIdWhenFindTrainingByIdThenReturnTrainingResponseAnd200() throws Exception {
        given(this.trainingService.findTrainingById(1L)).willReturn(this.legTraining);
        given(this.mapper.toResponse(this.legTraining)).willReturn(this.legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingResponse.write(this.legResponse).getJson()));
    }

    @Test
    void givenNonExistentIdWhenFindTrainingByIdThenReturn404() throws Exception {
        given(this.trainingService.findTrainingById(1L)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.TRAININGS + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        verify(this.mapper, times(0)).toResponse(this.legTraining);
    }

    @Test
    void givenLegTrainingRequestWhenCreateTrainingThenReturnLegTrainingResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.legRequest)).willReturn(this.legTraining);
        given(this.trainingService.createTraining(this.legTraining)).willReturn(this.legTraining);
        given(this.mapper.toResponse(this.legTraining)).willReturn(this.legResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(this.legRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonTrainingResponse.write(this.legResponse).getJson()));
    }

    @Test
    void givenIncorrectTrainingRequestWhenCreateTrainingThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(this.incorrectRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.mapper, times(0)).toDomain(this.incorrectRequest);
        verify(this.trainingService, times(0)).createTraining(this.legTraining);
        verify(this.mapper, times(0)).toResponse(this.legTraining);
    }

    @Test
    void givenNullTrainingRequestWhenCreateTrainingThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.TRAININGS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonTrainingRequest.write(this.nullRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.mapper, times(0)).toDomain(this.nullRequest);
        verify(this.trainingService, times(0)).createTraining(this.legTraining);
        verify(this.mapper, times(0)).toResponse(this.legTraining);
    }
}
