package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ExerciseServiceImplTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseDao exerciseDao;

    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    private final SerieModelRepository serieModelRepository = new SerieModelRepository();

    @Test
    void givenExistentIdWhenFindExerciseByIdThenReturnExercise() {
        Long id = 1L;
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.exerciseDao.findExerciseById(id)).thenReturn(exercise);

        assertThat(this.exerciseService.findExerciseById(id), is(exercise));
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

        when(this.exerciseDao.createExercise(exercise)).thenReturn(exercise);

        assertThat(this.exerciseService.createExercise(exercise), is(exercise));
    }

    @Test
    void givenTrainingIdWhenFindExercisesByTrainingIdThenReturnExerciseList() {
        Long trainingId = 1L;
        Exercise exercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();

        when(this.exerciseDao.findExercisesByTrainingId(trainingId)).thenReturn(List.of(exercise));

        assertThat(this.exerciseService.findExercisesByTrainingId(trainingId), is(List.of(exercise)));
    }

    @Test
    void givenNonExistentExerciseIdWhenFindSeriesByExerciseIdThenThrowNotFoundException() {
        Long exerciseId = 1L;

        when(this.exerciseDao.findSeriesByExerciseId(exerciseId)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.findSeriesByExerciseId(exerciseId));
    }

    @Test
    void givenExistentExerciseIdWhenFindSeriesByExerciseIdThenReturnSerieList() {
        Long exerciseId = 1L;
        Serie serie = this.serieModelRepository.getSquatSerie();

        when(this.exerciseDao.findSeriesByExerciseId(exerciseId)).thenReturn(List.of(serie));

        assertThat(this.exerciseService.findSeriesByExerciseId(exerciseId), is(List.of(serie)));
    }
}
