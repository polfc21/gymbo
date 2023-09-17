package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.UserDao;
import com.behabits.gymbo.domain.exceptions.ExistingUserException;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.application.domain.UserDetailsImpl;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private AuthorityService authorityService;

    private final User user = new UserModelRepository().getUser();

    @Test
    void givenNonExistentUsernameWhenCreateUserThenReturnUser() {
        when(this.userDao.findByUsername(this.user.getUsername())).thenReturn(null);
        when(this.userDao.saveUser(this.user)).thenReturn(this.user);

        assertThat(this.userService.createUser(this.user), is(this.user));
    }

    @Test
    void givenExistentUsernameWhenCreateUserThenThrowExistingUserException() {
        when(this.userDao.findByUsername(this.user.getUsername())).thenReturn(this.user);

        assertThrows(ExistingUserException.class,() -> this.userService.createUser(this.user));
    }

    @Test
    void givenExistentUsernameWhenLoadUserByUsernameThenReturnUserDetails() {
        String existentUsername = this.user.getUsername();

        when(this.userDao.findByUsername(existentUsername)).thenReturn(this.user);

        assertThat(this.userService.loadUserByUsername(existentUsername), is(new UserDetailsImpl(this.user)));
    }

    @Test
    void givenNonExistentUsernameWhenLoadUserByUsernameThenThrowUsernameNotFoundException() {
        String nonExistentUsername = "nonExistentUsername";

        when(this.userDao.findByUsername(nonExistentUsername)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername(nonExistentUsername));
    }

    @Test
    void givenExistentUsernameWhenFindUserByUsernameThenReturnUser() {
        String existentUsername = this.user.getUsername();

        when(this.userDao.findByUsername(existentUsername)).thenReturn(this.user);

        assertThat(this.userService.findUserByUsername(existentUsername), is(this.user));
    }

    @Test
    void givenNonExistentUsernameWhenFindUserByUsernameThenThrowUsernameNotFoundException() {
        String nonExistentUsername = "nonExistentUsername";

        when(this.userDao.findByUsername(nonExistentUsername)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> this.userService.findUserByUsername(nonExistentUsername));
    }

    @Test
    void givenKilometersWhenFindUsersInKilometersOrderedByDistanceFromLoggedUserThenReturnUsers() {
        Double kilometers = 1.0;
        Long loggedUserId = this.user.getId();
        User nearUser = new UserModelRepository().getReviewer();

        when(this.authorityService.getLoggedUser()).thenReturn(this.user);
        when(this.userDao.findUsersInKilometersOrderedByDistanceFromLoggedUser(loggedUserId, kilometers)).thenReturn(List.of(nearUser));

        assertThat(this.userService.findUsersInKilometersOrderedByDistanceFromLoggedUser(kilometers), is(List.of(nearUser)));
    }

    @Test
    void givenFootballSportWhenFindUsersBySportThenReturnUsers() {
        User loggedUser = this.user;
        User footballUser = new UserModelRepository().getFootballUser();

        when(this.authorityService.getLoggedUser()).thenReturn(loggedUser);
        when(this.userDao.findAll()).thenReturn(List.of(loggedUser, footballUser));

        assertThat(this.userService.findUsersBySport(Sport.FOOTBALL), is(List.of(footballUser)));
    }

}
