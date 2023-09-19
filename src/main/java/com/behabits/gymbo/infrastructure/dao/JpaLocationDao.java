package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.LocationDao;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.infrastructure.repository.LocationRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LocationEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.LocationEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaLocationDao implements LocationDao {

    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;

    @Override
    public Location findLocationById(Long id) {
        LocationEntity entity = this.locationRepository.findById(id)
                .orElse(null);
        return entity != null ? this.locationEntityMapper.toDomain(entity) : null;
    }

    @Override
    public Location saveLocation(Location location) {
        LocationEntity entityToSave = this.locationEntityMapper.toEntity(location);
        LocationEntity entitySaved = this.locationRepository.save(entityToSave);
        return this.locationEntityMapper.toDomain(entitySaved);
    }

    @Override
    public void deleteLocation(Location location) {
        this.locationRepository.deleteById(location.getId());
    }

}
