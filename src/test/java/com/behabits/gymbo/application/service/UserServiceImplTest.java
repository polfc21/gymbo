package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.UserDao;
import com.behabits.gymbo.domain.exceptions.ExistingUserException;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.application.domain.UserDetails;
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

    private final UserModelRepository userModelRepository = new UserModelRepository();

    @Test
    void givenNonExistentUsernameWhenCreateUserThenReturnUser() {
        User user = this.userModelRepository.getUser();

        when(this.userDao.createUser(user)).thenReturn(user);

        assertThat(this.userService.createUser(user), is(user));
    }

    @Test
    void givenExistentUsernameWhenCreateUserThenThrowExistingUserException() {
        User user = this.userModelRepository.getUser();

        when(this.userDao.createUser(user)).thenThrow(ExistingUserException.class);

        assertThrows(ExistingUserException.class, () -> this.userService.createUser(user));
    }

    @Test
    void givenExistentUsernameWhenLoadUserByUsernameThenReturnUserDetails() {
        User user = this.userModelRepository.getUser();

        when(this.userDao.findByUsername(user.getUsername())).thenReturn(user);

        assertThat(this.userService.loadUserByUsername(user.getUsername()), is(new UserDetails(user)));
    }

    @Test
    void givenNonExistentUsernameWhenLoadUserByUsernameThenThrowUsernameNotFoundException() {
        User user = this.userModelRepository.getUser();
        String username = user.getUsername();

        when(this.userDao.findByUsername(user.getUsername())).thenReturn(null);


        assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername(username));
    }
}
