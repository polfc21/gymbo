package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.UserDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.UserService;
import com.behabits.gymbo.application.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return new UserDetailsImpl(user);
    }

    @Override
    public User findUserByUsername(String username) {
        User user = this.userDao.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with username " + username + " not found");
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        return this.userDao.createUser(user);
    }

    @Override
    public List<User> findUsersInKilometersOrderedByDistanceFromLoggedUser(Double kilometers) {
        Long loggedUserId = this.authorityService.getLoggedUser().getId();
        return this.userDao.findUsersInKilometersOrderedByDistanceFromLoggedUser(loggedUserId, kilometers);
    }

    @Override
    public List<User> findUsersBySport(Sport sport) {
        User loggedUser = this.authorityService.getLoggedUser();
        return this.userDao.findAll()
                .stream()
                .filter(user -> user.getSports().contains(sport) && !Objects.equals(user.getUsername(), loggedUser.getUsername()))
                .toList();
    }

}
