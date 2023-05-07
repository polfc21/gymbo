package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.ExerciseResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ExerciseResponseTest {

    private final ExerciseResponseRepository exerciseResponseRepository = new ExerciseResponseRepository();

    @Test
    void givenSameExerciseResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        ExerciseResponse exerciseResponse = this.exerciseResponseRepository.getSquatExerciseResponse();
        ExerciseResponse exerciseResponse2 = this.exerciseResponseRepository.getSquatExerciseResponse();

        assertThat(exerciseResponse, is(exerciseResponse2));
        assertThat(exerciseResponse.hashCode(), is(exerciseResponse2.hashCode()));
    }

}
