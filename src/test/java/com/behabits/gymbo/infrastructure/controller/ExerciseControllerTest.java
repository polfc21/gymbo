package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.infrastructure.controller.repositories.request.ExerciseRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.request.SerieRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.ExerciseResponseRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.SerieResponseRepository;
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
        ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();
        SerieRequestRepository serieRequestRepository = new SerieRequestRepository();
        ExerciseRequest squatRequest = exerciseRequestRepository.getSquatExerciseRequest();
        SerieRequest squatSerieRequest = serieRequestRepository.getSquatSerieRequest();
        squatRequest.setSeries(List.of(squatSerieRequest));
        return squatRequest;
    }

    public ExerciseResponse buildSquatResponse() {
        ExerciseResponseRepository exerciseResponseRepository = new ExerciseResponseRepository();
        SerieResponseRepository serieResponseRepository = new SerieResponseRepository();
        ExerciseResponse squatResponse = exerciseResponseRepository.getSquatExerciseResponse();
        SerieResponse squatSerieResponse = serieResponseRepository.getSquatSerieResponse();
        squatResponse.setSeries(List.of(squatSerieResponse));
        return squatResponse;
    }

    public Exercise buildSquatExercise() {
        ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();
        SerieModelRepository serieModelRepository = new SerieModelRepository();
        Exercise squatExercise = exerciseModelRepository.buildSquatExercise();
        Serie squatSerie = serieModelRepository.buildSquatSerie();
        squatExercise.setSeries(List.of(squatSerie));
        return squatExercise;
    }

    public ExerciseRequest buildIncorrectExerciseRequest() {
        ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();
        return exerciseRequestRepository.getIncorrectExerciseRequest();
    }

    public ExerciseRequest buildNullExerciseRequest() {
        ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();
        return exerciseRequestRepository.getNullExerciseRequest();
    }

    public SerieRequest buildIncorrectSerieRequest() {
        SerieRequestRepository serieRequestRepository = new SerieRequestRepository();
        return serieRequestRepository.getIncorrectSerieRequest();
    }

    public SerieRequest buildNullSerieRequest() {
        SerieRequestRepository serieRequestRepository = new SerieRequestRepository();
        return serieRequestRepository.getNullSerieRequest();
    }

    @Test
    void givenExistentIdWhenFindExerciseByIdThenReturnExerciseResponseAnd200() throws Exception {
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
    void givenNotExistentIdWhenFindExerciseByIdThenReturnExerciseResponseAnd200() throws Exception {
        given(this.exerciseService.findExerciseById(1L)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.EXERCISES + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        verify(this.mapper, times(0)).toResponse(this.squatExercise);
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
