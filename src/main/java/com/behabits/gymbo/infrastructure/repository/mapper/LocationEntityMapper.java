package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.infrastructure.repository.entity.LocationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public Location toDomain(LocationEntity entity) {
        Location domain = new Location();
        domain.setId(entity.getId());
        domain.setCity(entity.getCity());
        domain.setCountry(entity.getCountry());
        domain.setGeometry(entity.getGeometry());
        domain.setUser(this.userEntityMapper.toDomain(entity.getPlayer()));
        return domain;
    }

    public LocationEntity toEntity(Location domain) {
        LocationEntity entity = new LocationEntity();
        entity.setId(domain.getId());
        entity.setCity(domain.getCity());
        entity.setCountry(domain.getCountry());
        entity.setGeometry(domain.getGeometry());
        entity.setPlayer(this.userEntityMapper.toEntity(domain.getUser()));
        return entity;
    }

}
