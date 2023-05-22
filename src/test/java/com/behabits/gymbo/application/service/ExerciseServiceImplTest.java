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

    private final Exercise exercise = new ExerciseModelRepository().getSquatExerciseWithSquatSeries();
    private final Serie serie = new SerieModelRepository().getSquatSerie();
    private final User loggedUser = new UserModelRepository().getUser();

    @Test
    void givenExistentIdAndUserHasPermissionsWhenFindExerciseByIdThenReturnExercise() {
        Long existentId = 1L;

        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);
        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);

        assertThat(this.exerciseService.findExerciseById(existentId), is(this.exercise));
    }

    @Test
    void givenExistentIdAndUserHasNotPermissionsWhenFindExerciseByIdThenThrowPermissionsException() {
        Long nonExistentId = 1L;

        when(this.exerciseDao.findExerciseById(nonExistentId)).thenReturn(this.exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.findExerciseById(nonExistentId));
    }

    @Test
    void givenNonExistentIdWhenFindExerciseByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.exerciseDao.findExerciseById(nonExistentId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.findExerciseById(1L));
    }

    @Test
    void givenExerciseWhenCreateExerciseThenReturnExercise() {
        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.exerciseDao.createExercise(this.exercise)).thenReturn(this.exercise);

        Exercise createdExercise = this.exerciseService.createExercise(this.exercise);
        assertThat(createdExercise, is(this.exercise));
        assertThat(createdExercise.getUser(), is(this.loggedUser));
    }

    @Test
    void givenTrainingIdWhenFindExercisesByTrainingIdThenReturnExerciseList() {
        Long trainingId = 1L;

        when(this.authorityService.getLoggedUser()).thenReturn(this.loggedUser);
        when(this.exerciseDao.findExercisesByTrainingIdAndUserId(trainingId, this.loggedUser.getId())).thenReturn(List.of(this.exercise));

        assertThat(this.exerciseService.findExercisesByTrainingId(trainingId), is(List.of(this.exercise)));
    }

    @Test
    void givenNonExistentExerciseIdWhenFindSeriesByExerciseIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.exerciseDao.findExerciseById(nonExistentId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.findSeriesByExerciseId(nonExistentId));
    }

    @Test
    void givenExistentExerciseIdAndLoggedUserHasPermissionsWhenFindSeriesByExerciseIdThenReturnSerieList() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(this.exercise.getId())).thenReturn(this.exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);
        when(this.exerciseDao.findSeriesByExerciseId(existentId)).thenReturn(List.of(this.serie));

        assertThat(this.exerciseService.findSeriesByExerciseId(existentId), is(List.of(this.serie)));
    }

    @Test
    void givenExistentExerciseIdAndLoggedUserHasNotPermissionsWhenFindSeriesByExerciseIdThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.findSeriesByExerciseId(existentId));
    }

    @Test
    void givenExistentExerciseIdAndUserHasPermissionsWhenCreateSerieThenReturnSerie() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);
        when(this.exerciseDao.createSerie(existentId, this.serie)).thenReturn(this.serie);

        assertThat(this.exerciseService.createSerie(existentId, this.serie), is(this.serie));
    }

    @Test
    void givenExistentExerciseIdAndUserHasNotPermissionsWhenCreateSerieThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.createSerie(existentId, this.serie));
    }

    @Test
    void givenNonExistentExerciseIdWhenCreateSerieThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.exerciseDao.findExerciseById(nonExistentId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.createSerie(nonExistentId, this.serie));
    }

    @Test
    void givenNonExistentExerciseIdWhenDeleteExerciseThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        doThrow(NotFoundException.class).when(this.exerciseDao).findExerciseById(nonExistentId);

        assertThrows(NotFoundException.class, () -> this.exerciseService.deleteExercise(nonExistentId));
    }

    @Test
    void givenExistentExerciseIdAndUserHasNotPermissionsWhenDeleteExerciseThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.deleteExercise(existentId));
    }

    @Test
    void givenExistentExerciseIdAndUserHasPermissionsWhenDeleteExerciseThenDoNothing() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);
        doNothing().when(this.exerciseDao).deleteExercise(this.exercise);

        try {
            this.exerciseService.deleteExercise(existentId);
        } catch (NotFoundException e) {
            fail("Should not throw NotFoundException");
        }
    }

    @Test
    void givenNonExistentIdWhenUpdateExerciseThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.exerciseDao.findExerciseById(nonExistentId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.updateExercise(nonExistentId, this.exercise));
    }

    @Test
    void givenExistentIdAndLoggedUserHasPermissionsWhenUpdateExerciseThenReturnExercise() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doNothing().when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);
        when(this.exerciseDao.updateExercise(existentId, this.exercise)).thenReturn(this.exercise);

        assertThat(this.exerciseService.updateExercise(existentId, this.exercise), is(this.exercise));
    }

    @Test
    void givenExistentIdAndLoggedUserHasNotPermissionsWhenUpdateExerciseThenThrowPermissionsException() {
        Long existentId = 1L;

        when(this.exerciseDao.findExerciseById(existentId)).thenReturn(this.exercise);
        doThrow(PermissionsException.class).when(this.authorityService).checkLoggedUserHasPermissions(this.exercise);

        assertThrows(PermissionsException.class, () -> this.exerciseService.updateExercise(existentId, this.exercise));
    }
}
