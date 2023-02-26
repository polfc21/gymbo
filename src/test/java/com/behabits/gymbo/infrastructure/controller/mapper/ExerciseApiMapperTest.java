package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.infrastructure.controller.builder.request.ExerciseRequestBuilder;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class ExerciseApiMapperTest {

    @Autowired
    private ExerciseApiMapper exerciseApiMapper;

    private ExerciseRequestBuilder exerciseRequestBuilder;

    private ExerciseModelRepository exerciseModelRepository;

    @BeforeEach
    void setUp() {
        this.exerciseRequestBuilder = new ExerciseRequestBuilder();
        this.exerciseModelRepository = new ExerciseModelRepository();
    }

    @Test
    void givenSquatExerciseRequestWhenMapToDomainThenReturnSquatExercise() {
        ExerciseRequest exerciseRequest = this.exerciseRequestBuilder.buildSquatExerciseRequest();

        Exercise exercise = this.exerciseApiMapper.toDomain(exerciseRequest);

        assertThat(exercise.getId(), is(exerciseRequest.getId()));
        assertThat(exercise.getName(), is(exerciseRequest.getName()));
        assertThat(exercise.getSeries(), is(exerciseRequest.getSeries()));
    }

    @Test
    void givenSquatExerciseWhenMapToResponseThenReturnSquatExerciseResponse() {
        Exercise exercise = this.exerciseModelRepository.buildSquatExercise();

        ExerciseResponse exerciseResponse = this.exerciseApiMapper.toResponse(exercise);

        assertThat(exerciseResponse.getId(), is(exercise.getId()));
        assertThat(exerciseResponse.getName(), is(exercise.getName()));
        assertThat(exerciseResponse.getSeries(), is(exercise.getSeries()));
    }
}