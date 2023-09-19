package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.UserDao;
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
    public User saveUser(User user) {
        UserEntity entityToSave = this.mapper.toEntity(user);
        UserEntity entitySaved = this.userRepository.save(entityToSave);
        return this.mapper.toDomain(entitySaved);
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

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

}
