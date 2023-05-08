package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;

public interface AuthorityDao {
    User getLoggedUser();
    void checkLoggedUserHasPermissions(Training training);
}
