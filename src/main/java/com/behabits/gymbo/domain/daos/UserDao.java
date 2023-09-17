package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.User;

import java.util.List;

public interface UserDao {
    User saveUser(User user);
    User findByUsername(String username);
    List<User> findUsersInKilometersOrderedByDistanceFromLoggedUser(Long userId, Double kilometers);
    List<User> findAll();
}
