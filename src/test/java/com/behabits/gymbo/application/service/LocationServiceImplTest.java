package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.LocationDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.LocationModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationDao locationDao;

    @Mock
    private AuthorityService authorityService;

    private final Location location = new LocationModelRepository().getBarcelona();
    private final User loggedUser = new UserModelRepository().getUser();

    @Test
    void givenExistentIdAndUserHasPermissionsWhenFindLocationByIdThenReturnLocation() {
        Long existentId = 1L;

        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.location);
        when(this.locationDao.findLocationById(existentId)).thenReturn(this.location);

        assertThat(this.locationService.findLocationById(existentId), is(this.location));
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenFindLocationByIdThenThrowPermissionsException() {
        Long nonExistentId = 1L;

        when(this.locationDao.findLocationById(nonExistentId)).thenReturn(this.location);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.location);

        assertThrows(PermissionsException.class, () -> this.locationService.findLocationById(nonExistentId));
    }

    @Test
    void givenNonExistentIdWhenFindLocationByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.locationDao.findLocationById(nonExistentId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.locationService.findLocationById(nonExistentId));
    }

    @Test
    void givenLocationWhenCreateLocationThenReturnLocation() {
        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.locationDao.saveLocation(this.location)).thenReturn(this.location);

        Location location = this.locationService.createLocation(this.location);
        assertThat(location, is(this.location));
        assertThat(location.getUser(), is(this.loggedUser));
    }

    @Test
    void givenExistentIdAndUserHasPermissionsWhenUpdateLocationThenReturnLocation() {
        Location locationToUpdate = mock(Location.class);
        Long existentId = 1L;

        when(this.locationDao.findLocationById(existentId)).thenReturn(locationToUpdate);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(locationToUpdate);
        when(this.locationDao.saveLocation(locationToUpdate)).thenReturn(this.location);

        assertThat(this.locationService.updateLocation(existentId, this.location), is(this.location));
        verify(locationToUpdate).setCity(this.location.getCity());
        verify(locationToUpdate).setCountry(this.location.getCountry());
        verify(locationToUpdate).setGeometry(this.location.getGeometry());
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenUpdateLocationThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.locationDao.findLocationById(existentId)).thenReturn(this.location);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.location);

        assertThrows(PermissionsException.class, () -> this.locationService.updateLocation(existentId, this.location));
    }

    @Test
    void givenNonExistentIdWhenUpdateLocationThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.locationDao.findLocationById(nonExistentId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.locationService.updateLocation(nonExistentId, this.location));
    }

    @Test
    void givenExistentIdAndUserHasPermissionsWhenDeleteLocationThenDeleteLocation() {
        Long existentId = 1L;

        when(this.locationDao.findLocationById(existentId)).thenReturn(this.location);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.location);
        doNothing().when(this.locationDao).deleteLocation(this.location);

        this.locationService.deleteLocation(existentId);
        verify(this.locationDao).deleteLocation(this.location);
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenDeleteLocationThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.locationDao.findLocationById(existentId)).thenReturn(this.location);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.location);

        assertThrows(PermissionsException.class, () -> this.locationService.deleteLocation(existentId));
    }

    @Test
    void givenNonExistentIdWhenDeleteLocationThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.locationDao.findLocationById(nonExistentId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.locationService.deleteLocation(nonExistentId));
    }
}
