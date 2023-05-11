package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.exceptions.PermissionsException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.repositories.UserModelRepository;
import com.behabits.gymbo.domain.services.AuthorityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceImplTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseDao exerciseDao;

    @Mock
    private AuthorityService authorityService;

    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    private final SerieModelRepository serieModelRepository = new SerieModelRepository();

    private final User loggedUser = new UserModelRepository().getUser();

    @Test
    void givenExistentIdAndUserHasPermissionsWhenFindExerciseByIdThenReturnExercise() {
        Long id = 1L;
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(exercise);
        when(this.exerciseDao.findExerciseById(id)).thenReturn(exercise);

        assertThat(this.exerciseService.findExerciseById(id), is(exercise));
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenFindExerciseByIdThenThrowPermissionsException() {
        Long id = 1L;
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.exerciseDao.findExerciseById(id)).thenReturn(exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.findExerciseById(id));
    }

    @Test
    void givenNonExistentIdWhenFindExerciseByIdThenThrowNotFoundException() {
        Long id = 1L;

        when(this.exerciseDao.findExerciseById(id)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.findExerciseById(1L));
    }

    @Test
    void givenExerciseWhenCreateExerciseThenReturnExercise() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.exerciseDao.createExercise(exercise)).thenReturn(exercise);

        Exercise createdExercise = this.exerciseService.createExercise(exercise);
        assertThat(createdExercise, is(exercise));
        assertThat(createdExercise.getUser(), is(this.loggedUser));
    }

    @Test
    void givenTrainingIdWhenFindExercisesByTrainingIdThenReturnExerciseList() {
        Long trainingId = 1L;
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.exerciseDao.findExercisesByTrainingIdAndUserId(trainingId, this.loggedUser.getId())).thenReturn(List.of(exercise));

        assertThat(this.exerciseService.findExercisesByTrainingId(trainingId), is(List.of(exercise)));
    }

    @Test
    void givenNonExistentExerciseIdWhenFindSeriesByExerciseIdThenThrowNotFoundException() {
        Long exerciseId = 1L;

        when(this.exerciseDao.findExerciseById(exerciseId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.findSeriesByExerciseId(exerciseId));
    }

    @Test
    void givenExistentExerciseIdAndLoggedUserHasPermissionsWhenFindSeriesByExerciseIdThenReturnSerieList() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        Serie serie = this.serieModelRepository.getSquatSerie();

        when(this.exerciseDao.findExerciseById(exercise.getId())).thenReturn(exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(exercise);
        when(this.exerciseDao.findSeriesByExerciseId(exercise.getId())).thenReturn(List.of(serie));

        assertThat(this.exerciseService.findSeriesByExerciseId(exercise.getId()), is(List.of(serie)));
    }

    @Test
    void givenExistentExerciseIdAndLoggedUserHasNotPermissionsWhenFindSeriesByExerciseIdThenThrowPermissionsException() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.exerciseDao.findExerciseById(exercise.getId())).thenReturn(exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.findSeriesByExerciseId(exercise.getId()));
    }

    @Test
    void givenExistentExerciseIdAndUserHasPermissionsWhenCreateSerieThenReturnSerie() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        Serie serie = this.serieModelRepository.getSquatSerie();

        when(this.exerciseDao.findExerciseById(exercise.getId())).thenReturn(exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(exercise);
        when(this.exerciseDao.createSerie(exercise.getId(), serie)).thenReturn(serie);

        assertThat(this.exerciseService.createSerie(exercise.getId(), serie), is(serie));
    }

    @Test
    void givenExistentExerciseIdAndUserHasNotPermissionsWhenCreateSerieThenThrowPermissionsException() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        Serie serie = this.serieModelRepository.getSquatSerie();

        when(this.exerciseDao.findExerciseById(exercise.getId())).thenReturn(exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.createSerie(exercise.getId(), serie));
    }

    @Test
    void givenNonExistentExerciseIdWhenCreateSerieThenThrowNotFoundException() {
        Long exerciseId = 1L;
        Serie serie = this.serieModelRepository.getSquatSerie();

        when(this.exerciseDao.findExerciseById(exerciseId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.createSerie(exerciseId, serie));
    }

    @Test
    void givenNonExistentExerciseIdWhenDeleteExerciseThenThrowNotFoundException() {
        Long exerciseId = 1L;

        doThrow(NotFoundException.class).when(this.exerciseDao).findExerciseById(exerciseId);

        assertThrows(NotFoundException.class, () -> this.exerciseService.deleteExercise(exerciseId));
    }

    @Test
    void givenExistentExerciseIdAndUserHasNotPermissionsWhenDeleteExerciseThenThrowPermissionsException() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.exerciseDao.findExerciseById(exercise.getId())).thenReturn(exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.deleteExercise(exercise.getId()));
    }

    @Test
    void givenExistentExerciseIdAndUserHasPermissionsWhenDeleteExerciseThenDoNothing() {
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.exerciseDao.findExerciseById(exercise.getId())).thenReturn(exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(exercise);
        doNothing().when(this.exerciseDao).deleteExercise(exercise.getId());

        try {
            this.exerciseService.deleteExercise(exercise.getId());
        } catch (NotFoundException e) {
            fail("Should not throw NotFoundException");
        }
    }
}
