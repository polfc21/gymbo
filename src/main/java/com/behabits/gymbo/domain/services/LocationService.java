package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Location;

public interface LocationService {
    Location createLocation(Location location);
    Location findLocationById(Long id);
    Location updateLocation(Long id, Location location);
    void deleteLocation(Long id);
}
