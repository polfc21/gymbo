package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.*;

public interface AuthorityService {
    User getLoggedUser();
    void checkLoggedUserHasPermissions(Training training);
    void checkLoggedUserHasPermissions(Exercise exercise);
    void checkLoggedUserHasPermissions(Serie serie);
    void checkLoggedUserHasPermissions(File file);
    void checkLoggedUserHasPermissions(Location location);
    void checkLoggedUserHasPermissions(Review review);
}
