package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.builder.ExerciseBuilder;
import com.behabits.gymbo.domain.builder.SerieBuilder;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.infrastructure.controller.builder.request.ExerciseRequestBuilder;
import com.behabits.gymbo.infrastructure.controller.builder.request.SerieRequestBuilder;
import com.behabits.gymbo.infrastructure.controller.builder.response.ExerciseResponseBuilder;
import com.behabits.gymbo.infrastructure.controller.builder.response.SerieResponseBuilder;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ExerciseApiMapper;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureJsonTesters
@WebMvcTest(ExerciseController.class)
public class ExerciseControllerTest {

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private ExerciseApiMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ExerciseResponse> jsonExerciseResponse;

    @Autowired
    private JacksonTester<ExerciseRequest> jsonExerciseRequest;

    private ExerciseRequest squatRequest;

    private ExerciseResponse squatResponse;

    private Exercise squatExercise;

    private ExerciseRequest incorrectRequest;

    private ExerciseRequest nullRequest;

    private SerieRequest incorrectSerieRequest;

    private SerieRequest nullSerieRequest;

    @BeforeEach
    void setUp() {
        this.squatRequest = this.buildSquatRequest();
        this.squatResponse = this.buildSquatResponse();
        this.squatExercise = this.buildSquatExercise();
        this.incorrectRequest = this.buildIncorrectExerciseRequest();
        this.incorrectSerieRequest = this.buildIncorrectSerieRequest();
        this.nullRequest = this.buildNullExerciseRequest();
        this.nullSerieRequest = this.buildNullSerieRequest();
    }

    public ExerciseRequest buildSquatRequest() {
        ExerciseRequestBuilder exerciseRequestBuilder = new ExerciseRequestBuilder();
        SerieRequestBuilder serieRequestBuilder = new SerieRequestBuilder();
        ExerciseRequest squatRequest = exerciseRequestBuilder.buildSquatExerciseRequest();
        SerieRequest squatSerieRequest = serieRequestBuilder.buildSquatSerieRequest();
        squatRequest.setSeries(List.of(squatSerieRequest));
        return squatRequest;
    }

    public ExerciseResponse buildSquatResponse() {
        ExerciseResponseBuilder exerciseResponseBuilder = new ExerciseResponseBuilder();
        SerieResponseBuilder serieResponseBuilder = new SerieResponseBuilder();
        ExerciseResponse squatResponse = exerciseResponseBuilder.buildSquatExerciseResponse();
        SerieResponse squatSerieResponse = serieResponseBuilder.buildSquatSerieResponse();
        squatResponse.setSeries(List.of(squatSerieResponse));
        return squatResponse;
    }

    public Exercise buildSquatExercise() {
        ExerciseBuilder exerciseBuilder = new ExerciseBuilder();
        SerieBuilder serieBuilder = new SerieBuilder();
        Exercise squatExercise = exerciseBuilder.buildSquatExercise();
        Serie squatSerie = serieBuilder.buildSquatSerie();
        squatExercise.setSeries(List.of(squatSerie));
        return squatExercise;
    }

    public ExerciseRequest buildIncorrectExerciseRequest() {
        ExerciseRequestBuilder exerciseRequestBuilder = new ExerciseRequestBuilder();
        return exerciseRequestBuilder.buildIncorrectExerciseRequest();
    }

    public ExerciseRequest buildNullExerciseRequest() {
        ExerciseRequestBuilder exerciseRequestBuilder = new ExerciseRequestBuilder();
        return exerciseRequestBuilder.buildNullExerciseRequest();
    }

    public SerieRequest buildIncorrectSerieRequest() {
        SerieRequestBuilder serieRequestBuilder = new SerieRequestBuilder();
        return serieRequestBuilder.buildIncorrectSerieRequest();
    }

    public SerieRequest buildNullSerieRequest() {
        SerieRequestBuilder serieRequestBuilder = new SerieRequestBuilder();
        return serieRequestBuilder.buildNullSerieRequest();
    }

    @Test
    void givenIdWhenFindExerciseByIdThenReturnExerciseResponseAnd200() throws Exception {
        given(this.exerciseService.findExerciseById(1L)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.jsonExerciseResponse.write(this.squatResponse).getJson()));
    }

    @Test
    void givenSquatExerciseRequestWhenCreateExerciseThenReturnSquatExerciseResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.createExercise(this.squatExercise)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonExerciseRequest.write(this.squatRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonExerciseResponse.write(this.squatResponse).getJson()));
    }

    @Test
    void givenNullSerieListRequestWhenCreateExerciseThenReturn201() throws Exception {
        this.squatRequest.setSeries(List.of());
        this.squatResponse.setSeries(List.of());
        given(this.mapper.toDomain(this.squatRequest)).willReturn(this.squatExercise);
        given(this.exerciseService.createExercise(this.squatExercise)).willReturn(this.squatExercise);
        given(this.mapper.toResponse(this.squatExercise)).willReturn(this.squatResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonExerciseRequest.write(this.squatRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertThat(response.getContentAsString(), is(this.jsonExerciseResponse.write(this.squatResponse).getJson()));
    }

    @Test
    void givenNullSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        this.squatRequest.setSeries(List.of(this.nullSerieRequest));
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonExerciseRequest.write(this.squatRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.mapper, times(0)).toDomain(this.squatRequest);
        verify(this.exerciseService, times(0)).createExercise(this.squatExercise);
        verify(this.mapper, times(0)).toResponse(this.squatExercise);
    }

    @Test
    void givenIncorrectSerieRequestWhenCreateExerciseThenReturn400() throws Exception {
        this.squatRequest.setSeries(List.of(this.incorrectSerieRequest));
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonExerciseRequest.write(this.squatRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.mapper, times(0)).toDomain(this.squatRequest);
        verify(this.exerciseService, times(0)).createExercise(this.squatExercise);
        verify(this.mapper, times(0)).toResponse(this.squatExercise);
    }

    @Test
    void givenIncorrectExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonExerciseRequest.write(this.incorrectRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.mapper, times(0)).toDomain(this.incorrectRequest);
        verify(this.exerciseService, times(0)).createExercise(this.squatExercise);
        verify(this.mapper, times(0)).toResponse(this.squatExercise);
    }

    @Test
    void givenNullExerciseRequestWhenCreateExerciseThenReturn400() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.EXERCISES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonExerciseRequest.write(this.nullRequest).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        verify(this.mapper, times(0)).toDomain(this.nullRequest);
        verify(this.exerciseService, times(0)).createExercise(this.squatExercise);
        verify(this.mapper, times(0)).toResponse(this.squatExercise);
    }
}
