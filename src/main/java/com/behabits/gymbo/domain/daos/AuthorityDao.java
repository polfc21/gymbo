package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.*;

public interface AuthorityDao {
    User getLoggedUser();
    void checkLoggedUserHasPermissions(Training training);
    void checkLoggedUserHasPermissions(Exercise exercise);
    void checkLoggedUserHasPermissions(Serie serie);
    void checkLoggedUserHasPermissions(File file);
    void checkLoggedUserHasPermissions(Location location);
    void checkLoggedUserHasPermissions(Review review);
    void checkLoggedUserHasPermissions(Publication publication);
    void checkLoggedUserHasPermissions(Link link);
}
