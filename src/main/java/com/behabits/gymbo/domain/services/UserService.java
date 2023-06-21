package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    User createUser(User user);
}
