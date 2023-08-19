package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.*;
import com.behabits.gymbo.domain.repositories.*;
import com.behabits.gymbo.infrastructure.repository.*;
import com.behabits.gymbo.infrastructure.repository.entity.*;
import com.behabits.gymbo.infrastructure.repository.mapper.UserEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.*;
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
    private FileRepository fileRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserEntityMapper userMapper;

    private final User user = new UserModelRepository().getUser();
    private final UserEntity loggedUser = new UserEntityRepository().getUser();
    private final Training training = new TrainingModelRepository().getLegTraining();
    private final TrainingEntity trainingEntity = new TrainingEntityRepository().getLegTraining();
    private final Exercise exercise = new ExerciseModelRepository().getSquatExercise();
    private final ExerciseEntity exerciseEntity = new ExerciseEntityRepository().getSquatExercise();
    private final Serie serie = new SerieModelRepository().getSquatSerie();
    private final SerieEntity serieEntity = new SerieEntityRepository().getSquatSerie();
    private final File file = new FileModelRepository().getFile();
    private final FileEntity fileEntity = new FileEntityRepository().getFile();
    private final Location location = new LocationModelRepository().getBarcelona();
    private final LocationEntity locationEntity = new LocationEntityRepository().getBarcelona();


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
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);

        assertThat(this.authorityDao.getLoggedUser(), is(this.user));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckHasTrainingPermissionsThenDoNothing() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.trainingRepository.findByIdAndPlayerId(this.training.getId(), this.user.getId())).thenReturn(this.trainingEntity);

        try {
            this.authorityDao.checkLoggedUserHasPermissions(this.training);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckHasTrainingPermissionsThenThrowException() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.trainingRepository.findByIdAndPlayerId(this.training.getId(), this.user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(this.training));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckHasExercisePermissionsThenDoNothing() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.exerciseRepository.findByIdAndPlayerId(exercise.getId(), this.user.getId())).thenReturn(this.exerciseEntity);

        try {
            this.authorityDao.checkLoggedUserHasPermissions(this.exercise);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckHasExercisePermissionsThenPermissionsException() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.exerciseRepository.findByIdAndPlayerId(this.exercise.getId(), this.user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(this.exercise));
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

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckFilePermissionsThenThrowPermissionsException() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.fileRepository.findByIdAndPlayerId(this.fileEntity.getId(), this.user.getId())).thenReturn(null);

        assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(this.file));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckFilePermissionsThenDoNothing() {
        when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
        when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
        when(this.fileRepository.findByIdAndPlayerId(this.fileEntity.getId(), this.user.getId())).thenReturn(this.fileEntity);

        try {
            this.authorityDao.checkLoggedUserHasPermissions(this.file);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void givenLoggedUserHasNotPermissionsWhenCheckLocationPermissionsThenThrowPermissionsException() {
    	when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
    	when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
    	when(this.locationRepository.findByIdAndPlayerId(this.locationEntity.getId(), this.user.getId())).thenReturn(null);

    	assertThrows(PermissionsException.class, () -> this.authorityDao.checkLoggedUserHasPermissions(this.location));
    }

    @Test
    void givenLoggedUserHasPermissionsWhenCheckLocationPermissionsThenDoNothing() {
    	when(this.userRepository.findByUsername(this.loggedUser.getUsername())).thenReturn(this.loggedUser);
    	when(this.userMapper.toDomain(this.loggedUser)).thenReturn(this.user);
    	when(this.locationRepository.findByIdAndPlayerId(this.locationEntity.getId(), this.user.getId())).thenReturn(this.locationEntity);

    	try {
    		this.authorityDao.checkLoggedUserHasPermissions(this.location);
    	} catch (Exception e) {
    		fail("Should not throw exception");
    	}
    }
}
