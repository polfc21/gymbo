package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceImplTest {

    @InjectMocks
    private AuthorityServiceImpl authorityService;

    @Mock
    private AuthorityDao authorityDao;

    @Test
    void givenLoggedUserWhenGetLoggedUserThenReturnLoggedUser() {
        User user = new User();

        when(this.authorityDao.getLoggedUser()).thenReturn(user);

        assertThat(this.authorityService.getLoggedUser(), is(user));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckTrainingPermissionsThenDoNothing() {
        Training training = new Training();

        doNothing().when(this.authorityDao).checkLoggedUserHasPermissions(training);

        try {
            this.authorityService.checkLoggedUserHasPermissions(training);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckTrainingPermissionsThenThrowException() {
        Training training = new Training();

        doThrow(PermissionsException.class).when(this.authorityDao).checkLoggedUserHasPermissions(training);

        assertThrows(PermissionsException.class, () -> this.authorityService.checkLoggedUserHasPermissions(training));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckExercisePermissionsThenDoNothing() {
        Exercise exercise = new Exercise();

        doNothing().when(this.authorityDao).checkLoggedUserHasPermissions(exercise);

        try {
            this.authorityService.checkLoggedUserHasPermissions(exercise);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckExercisePermissionsThenThrowException() {
        Exercise exercise = new Exercise();

        doThrow(PermissionsException.class).when(this.authorityDao).checkLoggedUserHasPermissions(exercise);

        assertThrows(PermissionsException.class, () -> this.authorityService.checkLoggedUserHasPermissions(exercise));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckSeriePermissionsThenDoNothing() {
        Serie serie = new Serie();

        doNothing().when(this.authorityDao).checkLoggedUserHasPermissions(serie);

        try {
            this.authorityService.checkLoggedUserHasPermissions(serie);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckSeriePermissionsThenThrowException() {
        Serie serie = new Serie();

        doThrow(PermissionsException.class).when(this.authorityDao).checkLoggedUserHasPermissions(serie);

        assertThrows(PermissionsException.class, () -> this.authorityService.checkLoggedUserHasPermissions(serie));
    }


    @Test
    void givenLoggedUserHasPermissionsWhenCheckFileThenDoNothing() {
        File file = new File();

        doNothing().when(this.authorityDao).checkLoggedUserHasPermissions(file);

        try {
            this.authorityService.checkLoggedUserHasPermissions(file);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckFileThenThrowException() {
        File file = new File();

        doThrow(PermissionsException.class).when(this.authorityDao).checkLoggedUserHasPermissions(file);

        assertThrows(PermissionsException.class, () -> this.authorityService.checkLoggedUserHasPermissions(file));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckLocationThenDoNothing() {
        Location location = new Location();

        doNothing().when(this.authorityDao).checkLoggedUserHasPermissions(location);

        try {
            this.authorityService.checkLoggedUserHasPermissions(location);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckLocationThenThrowException() {
        Location location = new Location();

        doThrow(PermissionsException.class).when(this.authorityDao).checkLoggedUserHasPermissions(location);

        assertThrows(PermissionsException.class, () -> this.authorityService.checkLoggedUserHasPermissions(location));
    }
}
