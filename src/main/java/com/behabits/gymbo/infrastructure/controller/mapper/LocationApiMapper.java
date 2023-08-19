package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.application.geometry.GeometryChecker;
import com.behabits.gymbo.application.geometry.GeometryParser;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.infrastructure.controller.dto.request.LocationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LocationResponse;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Component;

@Component
public class LocationApiMapper {

    public Location toDomain(LocationRequest request) {
        Location domain = new Location();
        domain.setCity(request.getCity());
        domain.setCountry(request.getCountry());
        domain.setGeometry(this.getGeometry(request.getGeometry()));
        return domain;
    }

    private Geometry getGeometry(Object geometry) {
        GeometryChecker geometryChecker = new GeometryChecker();
        GeometryParser geometryParser = new GeometryParser();
        if (geometryChecker.isWKT(geometry)) {
            return geometryParser.getGeometryFromWKT(geometry.toString());
        } else {
            return geometryParser.getGeometryFromGeoJson(geometry);
        }
    }

    public LocationResponse toResponse(Location domain) {
        LocationResponse response = new LocationResponse();
        response.setId(domain.getId());
        response.setCity(domain.getCity());
        response.setCountry(domain.getCountry());
        response.setGeometry(domain.getGeometry().toText());
        return response;
    }

}
