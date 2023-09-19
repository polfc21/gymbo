package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.LocationDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationDao locationDao;
    private final AuthorityService authorityService;

    @Override
    public Location createLocation(Location location) {
        User user = this.authorityService.getLoggedUser();
        location.setUser(user);
        return this.locationDao.saveLocation(location);
    }

    @Override
    public Location findLocationById(Long id) {
        Location location = this.locationDao.findLocationById(id);
        if (location == null) {
            throw new NotFoundException("Location not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(location);
        return location;
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        Location locationToUpdate = this.findLocationById(id);
        locationToUpdate.setCity(location.getCity());
        locationToUpdate.setCountry(location.getCountry());
        locationToUpdate.setGeometry(location.getGeometry());
        return this.locationDao.saveLocation(locationToUpdate);
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = this.findLocationById(id);
        this.locationDao.deleteLocation(location);
    }
}
