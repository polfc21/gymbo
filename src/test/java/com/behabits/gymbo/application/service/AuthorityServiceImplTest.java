package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
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
}
