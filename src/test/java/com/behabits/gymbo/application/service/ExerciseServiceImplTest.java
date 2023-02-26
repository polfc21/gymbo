package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import org.junit.jupiter.api.BeforeEach;
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
public class ExerciseServiceImplTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseDao exerciseDao;

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        this.exercise = new Exercise();
        this.exercise.setSeries(List.of(new Serie()));
    }

    @Test
    void givenExistentIdWhenFindExerciseByIdThenReturnExercise() {
        Long id = 1L;

        when(this.exerciseDao.findExerciseById(id)).thenReturn(this.exercise);

        assertThat(this.exerciseService.findExerciseById(id), is(this.exercise));
    }

    @Test
    void givenNonExistentIdWhenFindExerciseByIdThenReturnNull() {
        Long id = 1L;

        when(this.exerciseDao.findExerciseById(id)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.exerciseService.findExerciseById(1L));
    }

    @Test
    void givenExerciseWhenCreateExerciseThenReturnExercise() {
        when(this.exerciseDao.createExercise(this.exercise)).thenReturn(this.exercise);

        assertThat(this.exerciseService.createExercise(this.exercise), is(this.exercise));
    }
}
