package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.application.geometry.GeometryFactoryRepository;
import com.behabits.gymbo.infrastructure.controller.dto.response.LocationResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocationResponseRepository {

    public LocationResponse getLocationResponse() {
        return LocationResponse.builder()
                .id(1L)
                .city("Barcelona")
                .country("Catalunya")
                .geometry(new GeometryFactoryRepository().getBarcelonaWKT())
                .build();
    }
}
