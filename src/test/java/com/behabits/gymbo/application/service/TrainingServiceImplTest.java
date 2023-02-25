package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.models.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingDao trainingDao;

    private Training training;

    private List<Training> trainings;

    @BeforeEach
    void setUp() {
        this.training = new Training();
        this.trainings = List.of(this.training);
    }

    @Test
    void givenMonthWhenFindTrainingsByMonthThenReturnTrainingsOfMonth() {
        Month month = Month.FEBRUARY;

        when(this.trainingDao.findTrainingsByMonth(month)).thenReturn(this.trainings);

        assertThat(this.trainingService.findTrainingsByMonth(month), is(this.trainings));
    }

    @Test
    void givenIdWhenFindTrainingByIdThenReturnTraining() {
        Long id = 1L;

        when(this.trainingDao.findTrainingById(id)).thenReturn(this.training);

        assertThat(this.trainingService.findTrainingById(id), is(this.training));
    }

    @Test
    void givenTrainingWhenCreateTrainingThenReturnTraining() {
        when(this.trainingDao.createTraining(this.training)).thenReturn(this.training);

        assertThat(this.trainingService.createTraining(this.training), is(this.training));
    }
}
