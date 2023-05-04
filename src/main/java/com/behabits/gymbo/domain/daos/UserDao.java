package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.User;

public interface UserDao {
    User createUser(User user);
    User findByUsername(String username);
}
