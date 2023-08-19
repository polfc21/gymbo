package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.repositories.LocationModelRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.LocationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LocationResponse;
import com.behabits.gymbo.infrastructure.controller.repositories.request.LocationRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class LocationApiMapperTest {

    @Autowired
    private LocationApiMapper locationApiMapper;

    private final LocationRequestRepository locationRequestRepository = new LocationRequestRepository();

    private final LocationModelRepository locationModelRepository = new LocationModelRepository();

    @Test
    void givenLocationRequestWhenMapToDomainThenReturnLocation() {
        LocationRequest locationRequest = this.locationRequestRepository.getWKTLocationRequest();

        Location location = this.locationApiMapper.toDomain(locationRequest);

        assertThat(location.getCity(), is(locationRequest.getCity()));
        assertThat(location.getCountry(), is(locationRequest.getCountry()));
        assertThat(location.getGeometry().toText(), is(locationRequest.getGeometry()));
    }

    @Test
    void givenLocationWhenMapToResponseThenReturnLocationResponse() {
        Location location = this.locationModelRepository.getBarcelona();

        LocationResponse locationResponse = this.locationApiMapper.toResponse(location);

        assertThat(locationResponse.getId(), is(location.getId()));
        assertThat(locationResponse.getCity(), is(location.getCity()));
        assertThat(locationResponse.getCountry(), is(location.getCountry()));
        assertThat(locationResponse.getGeometry(), is(location.getGeometry().toText()));
    }
}
