package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.TrainingRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class TrainingRequestTest {

    private final TrainingRequestRepository trainingRequestRepository = new TrainingRequestRepository();

    @Test
    void givenSameTrainingRequestWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        TrainingRequest trainingRequest = this.trainingRequestRepository.getCorrectTrainingRequest();
        trainingRequest.setTrainingDate(null);
        TrainingRequest trainingRequest2 = this.trainingRequestRepository.getCorrectTrainingRequest();
        trainingRequest2.setTrainingDate(null);

        assertThat(trainingRequest, is(trainingRequest2));
        assertThat(trainingRequest.hashCode(), is(trainingRequest2.hashCode()));
    }

}
