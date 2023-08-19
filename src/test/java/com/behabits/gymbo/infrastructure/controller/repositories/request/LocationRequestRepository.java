package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.application.geometry.GeometryFactoryRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.LocationRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocationRequestRepository {

    public LocationRequest getWKTLocationRequest() {
        Object geometry = new GeometryFactoryRepository().getBarcelonaWKT();
        return new LocationRequest("Barcelona", "Catalunya", geometry);
    }

    public LocationRequest getGeoJsonLocationRequest() {
        Object geometry = new GeometryFactoryRepository().getBarcelonaGeoJson();
        return new LocationRequest("Barcelona", "Catalunya", geometry);
    }

    public LocationRequest getIncorrectLocationRequest() {
        return new LocationRequest("Barcelona", "Catalunya", "incorrect geometry");
    }

}
