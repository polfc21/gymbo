package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.TrainingResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class TrainingResponseTest {

    private final TrainingResponseRepository trainingResponseRepository = new TrainingResponseRepository();

    @Test
    void givenSameTrainingResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        TrainingResponse trainingResponse = this.trainingResponseRepository.getLegTrainingResponse();
        trainingResponse.setTrainingDate(null);
        TrainingResponse trainingResponse2 = this.trainingResponseRepository.getLegTrainingResponse();
        trainingResponse2.setTrainingDate(null);

        assertThat(trainingResponse, is(trainingResponse2));
        assertThat(trainingResponse.hashCode(), is(trainingResponse2.hashCode()));
    }

}
