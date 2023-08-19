package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.application.geometry.GeometryFactoryRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LocationEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

@NoArgsConstructor
public class LocationEntityRepository {

    private final UserEntity player = new UserEntityRepository().getUser();

    private final Geometry barcelona = new GeometryFactoryRepository().getBarcelonaGeometry();

    public LocationEntity getBarcelona() {
        return LocationEntity.builder()
                .id(1L)
                .city("Barcelona")
                .country("Catalunya")
                .geometry(this.barcelona)
                .player(this.player)
                .build();
    }

}
