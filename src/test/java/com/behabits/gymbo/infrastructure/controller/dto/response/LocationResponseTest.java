package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.LocationResponseRepository;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LocationResponseTest {

    private final LocationResponseRepository locationResponseRepository = new LocationResponseRepository();

    @Test
    void givenSameLocationResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        LocationResponse locationResponse = this.locationResponseRepository.getLocationResponse();
        LocationResponse locationResponse2 = this.locationResponseRepository.getLocationResponse();

        assertThat(locationResponse.getId(), is(locationResponse2.getId()));
        assertThat(locationResponse.getCity(), is(locationResponse2.getCity()));
        assertThat(locationResponse.getCountry(), is(locationResponse2.getCountry()));
        assertThat(locationResponse.getGeometry(), is(locationResponse2.getGeometry()));
    }
}
