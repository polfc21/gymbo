package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.AuthorityDao;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.UserRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
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

    private static final String USER_HAS_NOT_PERMISSIONS = "User doesn't have permissions";

    private final UserEntityMapper userMapper;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;

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
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

    @Override
    public void checkLoggedUserHasPermissions(Exercise exercise) {
        Long loggedUserId = this.getLoggedUser().getId();
        ExerciseEntity exerciseToCheck = this.exerciseRepository.findByIdAndPlayerId(exercise.getId(), loggedUserId);
        if (exerciseToCheck == null) {
            throw new PermissionsException(USER_HAS_NOT_PERMISSIONS);
        }
    }

}
