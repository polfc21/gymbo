package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.repositories.LocationModelRepository;
import com.behabits.gymbo.infrastructure.repository.LocationRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LocationEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.LocationEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.LocationEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class JpaLocationDaoTest {

    @InjectMocks
    private JpaLocationDao locationDao;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationEntityMapper locationEntityMapper;

    private final Location location = new LocationModelRepository().getBarcelona();
    private final LocationEntity locationEntity = new LocationEntityRepository().getBarcelona();

    @Test
    void givenSaveLocationWhenSaveLocationThenReturnLocation() {
        when(this.locationEntityMapper.toEntity(this.location)).thenReturn(this.locationEntity);
        when(this.locationRepository.save(this.locationEntity)).thenReturn(this.locationEntity);
        when(this.locationEntityMapper.toDomain(this.locationEntity)).thenReturn(this.location);

        assertThat(this.locationDao.saveLocation(this.location), is(this.location));
    }

    @Test
    void givenExistentIdWhenFindLocationByIdThenReturnLocation() {
        Long existentId = 1L;

        when(this.locationRepository.findById(existentId)).thenReturn(Optional.of(this.locationEntity));
        when(this.locationEntityMapper.toDomain(this.locationEntity)).thenReturn(this.location);

        assertThat(this.locationDao.findLocationById(existentId), is(this.location));
    }

    @Test
    void givenNonExistentIdWhenFindLocationByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.locationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.locationDao.findLocationById(nonExistentId));
    }

    @Test
    void givenLocationWhenDeleteLocationThenDeleteLocation() {
        this.locationDao.deleteLocation(this.location);

        verify(this.locationRepository).deleteById(this.location.getId());
    }
}
