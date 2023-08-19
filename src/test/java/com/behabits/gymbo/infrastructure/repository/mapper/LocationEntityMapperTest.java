package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.repositories.LocationModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LocationEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.LocationEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class LocationEntityMapperTest {

    @Autowired
    private LocationEntityMapper mapper;

    private final LocationEntity locationEntity = new LocationEntityRepository().getBarcelona();

    private final Location location = new LocationModelRepository().getBarcelona();

    @Test
    void givenLocationWhenMapToEntityThenReturnLocationEntity() {
        LocationEntity locationEntity = this.mapper.toEntity(this.location);

        assertThat(locationEntity.getId(), is(this.location.getId()));
        assertThat(locationEntity.getCity(), is(this.location.getCity()));
        assertThat(locationEntity.getCountry(), is(this.location.getCountry()));
        assertThat(locationEntity.getGeometry(), is(this.location.getGeometry()));
        assertThat(locationEntity.getPlayer().getId(), is(this.location.getUser().getId()));
    }

    @Test
    void givenLocationEntityWhenMapToDomainThenReturnLocation() {
        Location location = this.mapper.toDomain(this.locationEntity);

        assertThat(location.getId(), is(this.locationEntity.getId()));
        assertThat(location.getCity(), is(this.locationEntity.getCity()));
        assertThat(location.getCountry(), is(this.locationEntity.getCountry()));
        assertThat(location.getGeometry(), is(this.locationEntity.getGeometry()));
        assertThat(location.getUser().getId(), is(this.locationEntity.getPlayer().getId()));
    }

}
