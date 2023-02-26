package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingDao trainingDao;

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();

    @Test
    void givenMonthWhenFindTrainingsByMonthThenReturnTrainingsOfMonth() {
        Month month = Month.FEBRUARY;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.findTrainingsByMonth(month)).thenReturn(List.of(training));

        assertThat(this.trainingService.findTrainingsByMonth(month), is(List.of(training)));
    }

    @Test
    void givenExistentIdWhenFindTrainingByIdThenReturnTraining() {
        Long id = 1L;
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.findTrainingById(id)).thenReturn(training);

        assertThat(this.trainingService.findTrainingById(id), is(training));
    }

    @Test
    void givenNonExistentIdWhenFindTrainingByIdThenReturnNull() {
        Long id = 1L;

        when(this.trainingDao.findTrainingById(id)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> this.trainingService.findTrainingById(1L));
    }

    @Test
    void givenTrainingWhenCreateTrainingThenReturnTraining() {
        Training training = this.trainingModelRepository.getLegTrainingWithSquatExercise();

        when(this.trainingDao.createTraining(training)).thenReturn(training);

        assertThat(this.trainingService.createTraining(training), is(training));
    }
}
