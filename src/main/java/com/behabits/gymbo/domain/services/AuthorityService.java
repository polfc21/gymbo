package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;

public interface AuthorityService {
    User getLoggedUser();
    void checkLoggedUserHasPermissions(Training training);
}