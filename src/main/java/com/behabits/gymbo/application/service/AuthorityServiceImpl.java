package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.models.*;
import com.behabits.gymbo.domain.services.AuthorityService;
import jakarta.transaction.Transactional;
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

    @Override
    public void checkLoggedUserHasPermissions(Exercise exercise) {
        this.authorityDao.checkLoggedUserHasPermissions(exercise);
    }

    @Override
    public void checkLoggedUserHasPermissions(Serie serie) {
        this.authorityDao.checkLoggedUserHasPermissions(serie);
    }

    @Transactional
    @Override
    public void checkLoggedUserHasPermissions(File file) {
        this.authorityDao.checkLoggedUserHasPermissions(file);
    }

    @Override
    public void checkLoggedUserHasPermissions(Location location) {
        this.authorityDao.checkLoggedUserHasPermissions(location);
    }

    @Override
    public void checkLoggedUserHasPermissions(Review review) {
        this.authorityDao.checkLoggedUserHasPermissions(review);
    }

    @Override
    public void checkLoggedUserHasPermissions(Publication publication) {
        this.authorityDao.checkLoggedUserHasPermissions(publication);
    }

}
