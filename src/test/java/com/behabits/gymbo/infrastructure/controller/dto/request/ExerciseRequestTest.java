package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.ExerciseRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ExerciseRequestTest {

    private final ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();

    @Test
    void givenSameExerciseRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        ExerciseRequest exerciseRequest = this.exerciseRequestRepository.getCorrectExerciseRequest();
        ExerciseRequest exerciseRequest2 = this.exerciseRequestRepository.getCorrectExerciseRequest();
        assertThat(exerciseRequest, is(exerciseRequest2));
        assertThat(exerciseRequest.hashCode(), is(exerciseRequest2.hashCode()));
    }
}
