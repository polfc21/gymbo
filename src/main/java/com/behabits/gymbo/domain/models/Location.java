package com.behabits.gymbo.domain.models;

import lombok.*;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Long id;
    private String city;
    private String country;
    private Geometry geometry;
    private User user;
}
