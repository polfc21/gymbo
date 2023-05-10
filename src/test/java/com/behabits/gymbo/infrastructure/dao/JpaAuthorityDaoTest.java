package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.UserRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaAuthorityDaoTest {

    @InjectMocks
    private JpaAuthorityDao authorityDao;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserEntityMapper userMapper;

    private final UserEntityRepository userEntityRepository = new UserEntityRepository();

    private final UserModelRepository userModelRepository = new UserModelRepository();

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();
    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    @Test
    void givenLoggedUserWhenGetLoggedUserThenReturnLoggedUser() {
        UserEntity userEntity = this.userEntityRepository.getUser();
        User user = this.userModelRepository.getUser();
        Authentication authentication = new TestingAuthenticationToken(userEntity.getUsername(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(this.userRepository.findByUsername(userEntity.getUsername())).thenReturn(userEntity);
        when(this.userMapper.toDomain(userEntity)).thenReturn(user);

        assertThat(this.authorityDao.getLoggedUser(), is(user));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckHasTrainingPermissionsThenDoNothing() {
        Training training = this.trainingModelRepository.getLegTraining();
        UserEntity userEntity = this.userEntityRepository.getUser();
        User user = this.userModelRepository.getUser();
        Authentication authentication = new TestingAuthenticationToken(userEntity.getUsername(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(this.userRepository.findByUsername(userEntity.getUsername())).thenReturn(userEntity);
        when(this.userMapper.toDomain(userEntity)).thenReturn(user);
        when(this.trainingRepository.findByIdAndPlayerId(training.getId(), user.getId())).thenReturn(new TrainingEntity());

        try {
            this.authorityDao.checkLoggedUserHasPermissions(training);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckHasTrainingPermissionsThenThrowException() {
        Training training = this.trainingModelRepository.getLegTraining();
        UserEntity userEntity = this.userEntityRepository.getUser();
        User user = this.userModelRepository.getUser();
        Authentication authentication = new TestingAuthenticationToken(userEntity.getUsername(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(this.userRepository.findByUsername(userEntity.getUsername())).thenReturn(userEntity);
        when(this.userMapper.toDomain(userEntity)).thenReturn(user);
        when(this.trainingRepository.findByIdAndPlayerId(training.getId(), user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(training));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckHasExercisePermissionsThenDoNothing() {
        Exercise exercise = this.exerciseModelRepository.getSquatExercise();
        UserEntity loggedUser = this.userEntityRepository.getUser();
        User user = this.userModelRepository.getUser();
        Authentication authentication = new TestingAuthenticationToken(loggedUser.getUsername(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(this.userRepository.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        when(this.userMapper.toDomain(loggedUser)).thenReturn(user);
        when(this.exerciseRepository.findByIdAndPlayerId(exercise.getId(), user.getId())).thenReturn(new ExerciseEntity());

        try {
            this.authorityDao.checkLoggedUserHasPermissions(exercise);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckHasExercisePermissionsThenPermissionsException() {
        Exercise exercise = this.exerciseModelRepository.getSquatExercise();
        UserEntity loggedUser = this.userEntityRepository.getUser();
        User user = this.userModelRepository.getUser();
        Authentication authentication = new TestingAuthenticationToken(loggedUser.getUsername(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(this.userRepository.findByUsername(loggedUser.getUsername())).thenReturn(loggedUser);
        when(this.userMapper.toDomain(loggedUser)).thenReturn(user);
        when(this.exerciseRepository.findByIdAndPlayerId(exercise.getId(), user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(exercise));
    }
}
