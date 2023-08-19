package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.LocationRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LocationRequestTest {

    private final LocationRequestRepository locationRequestRepository = new LocationRequestRepository();

    @Test
    void givenSameLocationRequestWhenEqualsAndHashCodeThenReturnTrueHashCode() {
        LocationRequest locationRequest = this.locationRequestRepository.getWKTLocationRequest();
        LocationRequest locationRequest2 = this.locationRequestRepository.getWKTLocationRequest();
        assertThat(locationRequest.getCity(), is(locationRequest2.getCity()));
        assertThat(locationRequest.getCountry(), is(locationRequest2.getCountry()));
        assertThat(locationRequest.getGeometry(), is(locationRequest2.getGeometry()));
    }
}
