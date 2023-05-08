package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.UserRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JpaAuthorityDao implements AuthorityDao {

    private final UserEntityMapper userMapper;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    @Override
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity loggedUser = this.userRepository.findByUsername(authentication.getName());
        return this.userMapper.toDomain(loggedUser);
    }

    @Override
    public void checkLoggedUserHasPermissions(Training training) {
        Long loggedUserId = this.getLoggedUser().getId();
        TrainingEntity trainingToCheck = this.trainingRepository.findByIdAndPlayerId(training.getId(), loggedUserId);
        if (trainingToCheck == null) {
            throw new PermissionsException("User doesn't have permissions to this training");
        }
    }

}
