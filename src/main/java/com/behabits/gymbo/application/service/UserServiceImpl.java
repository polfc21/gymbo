package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.UserDao;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.UserService;
import com.behabits.gymbo.application.domain.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserDao userDao;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return new UserDetailsImpl(user);
    }

    @Override
    public User createUser(User user) {
        return this.userDao.createUser(user);
    }

}
