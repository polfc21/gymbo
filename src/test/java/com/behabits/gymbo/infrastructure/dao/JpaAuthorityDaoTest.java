package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.UserRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.SerieEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.UserEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private SerieRepository serieRepository;

    @Mock
    private UserEntityMapper userMapper;

    private final UserEntityRepository userEntityRepository = new UserEntityRepository();

    private final UserModelRepository userModelRepository = new UserModelRepository();

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();
    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();
    private final User user = new UserModelRepository().getUser();
    private final UserEntity loggedUser = new UserEntityRepository().getUser();
    private final Serie serie = new SerieModelRepository().getSquatSerie();
    private final SerieEntity serieEntity = new SerieEntityRepository().getSquatSerie();

    @BeforeEach
    void setUp() {
        Authentication authentication = new TestingAuthenticationToken(this.loggedUser.getUsername(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

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

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckSeriePermissionsThenThrowPermissionsException() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.serieRepository.findByIdAndPlayerId(this.serieEntity.getId(), this.user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(this.serie));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckSeriePermissionsThenDoNothing() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.serieRepository.findByIdAndPlayerId(this.serieEntity.getId(), this.user.getId())).thenReturn(this.serieEntity);

        try {
            this.authorityDao.checkLoggedUserHasPermissions(this.serie);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }
}
