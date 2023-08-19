package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.dto.validator.Geometry;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationRequest {
    private String city;
    private String country;
    @Geometry
    private Object geometry;
}
