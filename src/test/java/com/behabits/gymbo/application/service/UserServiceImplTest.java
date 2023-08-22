package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.UserDao;
import com.behabits.gymbo.domain.exceptions.ExistingUserException;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.application.domain.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    private final User user = new UserModelRepository().getUser();

    @Test
    void givenNonExistentUsernameWhenCreateUserThenReturnUser() {
        when(this.userDao.createUser(this.user)).thenReturn(this.user);

        assertThat(this.userService.createUser(this.user), is(this.user));
    }

    @Test
    void givenExistentUsernameWhenCreateUserThenThrowExistingUserException() {
        when(this.userDao.createUser(this.user)).thenThrow(ExistingUserException.class);

        assertThrows(ExistingUserException.class, () -> this.userService.createUser(this.user));
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
}
