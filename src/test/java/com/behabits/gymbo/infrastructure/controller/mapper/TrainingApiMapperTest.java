package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.infrastructure.controller.builder.request.TrainingRequestBuilder;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TrainingApiMapperTest {

    @Autowired
    private TrainingApiMapper trainingApiMapper;

    private TrainingRequestBuilder trainingRequestBuilder;

    private TrainingModelRepository trainingModelRepository;


    @BeforeEach
    void setUp() {
        this.trainingRequestBuilder = new TrainingRequestBuilder();
        this.trainingModelRepository = new TrainingModelRepository();
    }

    @Test
    void givenLegTrainingRequestWhenMapToDomainThenReturnLegTraining() {
        TrainingRequest trainingRequest = this.trainingRequestBuilder.buildLegTrainingRequest();

        Training training = this.trainingApiMapper.toDomain(trainingRequest);

        assertThat(training.getId(), is(trainingRequest.getId()));
        assertThat(training.getName(), is(trainingRequest.getName()));
        assertThat(training.getTrainingDate(), is(trainingRequest.getTrainingDate()));
        assertThat(training.getExercises(), is(trainingRequest.getExercises()));
    }

    @Test
    void givenLegTrainingWhenMapToResponseThenReturnLegTrainingResponse() {
        Training training = this.trainingModelRepository.buildLegTraining();

        TrainingResponse trainingResponse = this.trainingApiMapper.toResponse(training);

        assertThat(trainingResponse.getId(), is(training.getId()));
        assertThat(trainingResponse.getName(), is(training.getName()));
        assertThat(trainingResponse.getTrainingDate(), is(training.getTrainingDate()));
        assertThat(trainingResponse.getExercises(), is(training.getExercises()));
    }

}