package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.SerieRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ExerciseRequestTest {

    private final SerieRequestRepository serieRequestRepository = new SerieRequestRepository();

    @Test
    void givenSameSerieRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        SerieRequest serieRequest = this.serieRequestRepository.getCorrectSerieRequest();
        SerieRequest serieRequest2 = this.serieRequestRepository.getCorrectSerieRequest();

        assertThat(serieRequest, is(serieRequest2));
        assertThat(serieRequest.hashCode(), is(serieRequest2.hashCode()));
    }
}
