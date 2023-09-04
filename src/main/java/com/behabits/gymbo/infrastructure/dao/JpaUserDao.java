package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.UserDao;
import com.behabits.gymbo.domain.exceptions.ExistingUserException;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.infrastructure.repository.UserRepository;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaUserDao implements UserDao {

    private final UserRepository userRepository;
    private final UserEntityMapper mapper;


    @Override
    public User createUser(User user) {
        if (this.existsUsername(user.getUsername())) {
            throw new ExistingUserException("User with username " + user.getUsername() + " already exists");
        }
        UserEntity entity = this.mapper.toEntity(user);
        UserEntity createdEntity = this.userRepository.save(entity);
        return this.mapper.toDomain(createdEntity);
    }

    @Override
    public User findByUsername(String username) {
        UserEntity entity = this.userRepository.findByUsername(username);
        return this.mapper.toDomain(entity);
    }

    @Override
    public List<User> findUsersInKilometersOrderedByDistanceFromLoggedUser(Long userId, Double kilometers) {
        return this.userRepository.findUsersInKilometersOrderedByDistanceFromPlayerId(userId, kilometers)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    private Boolean existsUsername(String username) {
        return this.userRepository.findByUsername(username) != null;
    }

}
