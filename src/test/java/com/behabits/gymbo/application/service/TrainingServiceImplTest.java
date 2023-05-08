package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.time.Year;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingDao trainingDao;

    @Mock
    private AuthorityService authorityService;

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();

    private final User loggedUser = new UserModelRepository().getUser();

    @Test
    void givenMonthWhenFindTrainingsByMonthThenReturnTrainingsOfMonth() {
        Month month = Month.FEBRUARY;
        Year year = Year.now();
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.trainingDao.findTrainingsByMonthAndYearAndUserId(month, year, this.loggedUser.getId())).thenReturn(List.of(training));

        assertThat(this.trainingService.findTrainingsByMonthAndYear(month, year), is(List.of(training)));
    }

    @Test
    void givenExistentIdAndLoggedUserHasPermissionsWhenFindTrainingByIdThenReturnTraining() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(training);
        when(this.trainingDao.findTrainingById(id)).thenReturn(training);

        assertThat(this.trainingService.findTrainingById(id), is(training));
    }

    @Test
    void givenExistentIdAndLoggedUserHasNotPermissionsWhenFindTrainingByIdThenThrowPermissionsException() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(training);
        when(this.trainingDao.findTrainingById(id)).thenReturn(training);

        assertThrows(PermissionsException.class, () -> this.trainingService.findTrainingById(id));
    }

    @Test
    void givenNonExistentIdWhenFindTrainingByIdThenThrowNotFoundException() {
        Long id = 1L;

        when(this.trainingDao.findTrainingById(id)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.trainingService.findTrainingById(1L));
    }

    @Test
    void givenTrainingWhenCreateTrainingThenReturnTraining() {
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.trainingDao.createTraining(training)).thenReturn(training);

        Training createdTraining = this.trainingService.createTraining(training);
        assertThat(createdTraining, is(training));
        assertThat(createdTraining.getUser(), is(this.loggedUser));
    }

    @Test
    void givenNonExistentIdWhenUpdateTrainingThenThrowNotFoundException() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.updateTraining(id, training)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.trainingService.updateTraining(id, training));
    }

    @Test
    void givenExistentIdAndLoggedUserHasPermissionsWhenUpdateTrainingThenReturnTrainingUpdated() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(training);
        when(this.trainingDao.updateTraining(id, training)).thenReturn(training);

        assertThat(this.trainingService.updateTraining(id, training), is(training));
    }

    @Test
    void givenExistentIdAndLoggedUserHasNotPermissionsWhenUpdateTrainingThenThrowPermissionsException() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(training);

        assertThrows(PermissionsException.class, () -> this.trainingService.updateTraining(id, training));
    }

    @Test
    void givenNonExistentIdWhenDeleteTrainingThenThrowNotFoundException() {
        Long id = 1L;

        doThrow(NotFoundException.class).when(this.trainingDao).findTrainingById(id);

        assertThrows(NotFoundException.class, () -> this.trainingService.deleteTraining(id));
    }

    @Test
    void givenExistentIdAndLoggedUserHasPermissionsWhenDeleteTrainingThenTrainingDaoDeleteTraining() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(training);
        doNothing().when(this.trainingDao).deleteTraining(training);

        try {
            this.trainingService.deleteTraining(id);
        } catch (NotFoundException e) {
            fail("Exception thrown");
        }
    }

    @Test
    void givenExistentIdAndLoggedUserHasNotPermissionsWhenDeleteTrainingThenThrowPermissionsException() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(training);

        assertThrows(PermissionsException.class, () -> this.trainingService.deleteTraining(id));
    }

    @Test
    void givenExistentIdAndLoggedUserHasPermissionsWhenAddExerciseThenReturnTrainingWithExerciseAdded() {
        Long id = 1L;
        Exercise exercise = new ExerciseModelRepository().getSquatExercise();
        Training training = this.trainingModelRepository.getLegTraining();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(training);
        when(this.trainingDao.createTraining(training)).thenReturn(training);
        Training trainingWithExerciseAdded = this.trainingService.addExercise(id, exercise);

        assertThat(trainingWithExerciseAdded, is(training));
        assertThat(trainingWithExerciseAdded.getExercises(), hasItem(exercise));
    }

    @Test
    void givenNonExistentIdWhenAddExerciseThenThrowNotFoundException() {
        Long id = 1L;
        Exercise exercise = new ExerciseModelRepository().getSquatExercise();

        when(this.trainingDao.findTrainingById(id)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.trainingService.addExercise(id, exercise));
    }

    @Test
    void givenExistentIdAndLoggedUserHasNotPermissionsWhenAddExerciseThenThrowPermissionsException() {
        Long id = 1L;
        Exercise exercise = new ExerciseModelRepository().getSquatExercise();
        Training training = this.trainingModelRepository.getLegTraining();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(training);

        assertThrows(PermissionsException.class, () -> this.trainingService.addExercise(id, exercise));
    }
}
