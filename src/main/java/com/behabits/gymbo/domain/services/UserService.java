package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    User findUserByUsername(String username);
    User createUser(User user);
    List<User> findUsersInKilometersOrderedByDistanceFromLoggedUser(Double kilometers);
}
