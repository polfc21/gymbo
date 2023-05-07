package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.SerieResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class SerieResponseTest {

    private final SerieResponseRepository serieResponseRepository = new SerieResponseRepository();

    @Test
    void givenSameSerieResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        SerieResponse serieResponse = this.serieResponseRepository.getSquatSerieResponse();
        SerieResponse serieResponse2 = this.serieResponseRepository.getSquatSerieResponse();

        assertThat(serieResponse, is(serieResponse2));
        assertThat(serieResponse.hashCode(), is(serieResponse2.hashCode()));
    }

}
