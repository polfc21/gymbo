package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityDao authorityDao;

    @Override
    public User getLoggedUser() {
        return this.authorityDao.getLoggedUser();
    }

    @Override
    public void checkLoggedUserHasPermissions(Training training) {
        this.authorityDao.checkLoggedUserHasPermissions(training);
    }

}
