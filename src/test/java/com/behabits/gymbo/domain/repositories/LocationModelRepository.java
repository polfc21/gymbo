package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.application.geometry.GeometryFactoryRepository;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

@NoArgsConstructor
public class LocationModelRepository {

    private final User user = new UserModelRepository().getUser();
    private final Geometry barcelona = new GeometryFactoryRepository().getBarcelonaGeometry();

    public Location getBarcelona() {
        return Location.builder()
                .id(1L)
                .city("Barcelona")
                .country("Spain")
                .geometry(this.barcelona)
                .user(this.user)
                .build();
    }

}
