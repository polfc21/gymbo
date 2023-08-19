package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Location;

public interface LocationDao {
    Location findLocationById(Long id);
    Location saveLocation(Location location);
    void deleteLocation(Location location);
}
