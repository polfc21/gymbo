package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.repositories.TrainingModelRepository;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.TrainingEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.TrainingEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaTrainingDaoTest {

    @InjectMocks
    private JpaTrainingDao trainingDao;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingEntityMapper mapper;

    private final Training legTraining = new TrainingModelRepository().getLegTraining();
    private final TrainingEntity legTrainingEntity = new TrainingEntityRepository().getLegTraining();

    @Test
    void givenLegTrainingWhenCreateTrainingThenReturnTraining() {
        when(this.mapper.toEntity(this.legTraining)).thenReturn(this.legTrainingEntity);
        when(this.trainingRepository.save(this.legTrainingEntity)).thenReturn(this.legTrainingEntity);
        when(this.mapper.toDomain(this.legTrainingEntity)).thenReturn(this.legTraining);

        assertThat(this.trainingDao.createTraining(this.legTraining), is(this.legTraining));
    }

    @Test
    void givenLegTrainingWithSquatExerciseWhenCreateTrainingThenReturnTraining() {
        when(this.mapper.toEntity(this.legTraining)).thenReturn(this.legTrainingEntity);
        when(this.trainingRepository.save(this.legTrainingEntity)).thenReturn(this.legTrainingEntity);
        when(this.mapper.toDomain(this.legTrainingEntity)).thenReturn(this.legTraining);

        assertThat(this.trainingDao.createTraining(this.legTraining), is(this.legTraining));
    }

    @Test
    void givenMonthAndYearAndUserIdWhenFindTrainingsByMonthAndYearAndPlayerIdThenReturnTrainings() {
        Long userId = 1L;
        Month actualMonth = LocalDate.now().getMonth();
        Year actualYear = Year.now();

        when(this.trainingRepository.findAllByMonthAndYearAndPlayerId(actualMonth.getValue(), actualYear.getValue(), userId)).thenReturn(List.of(this.legTrainingEntity));
        when(this.mapper.toDomain(this.legTrainingEntity)).thenReturn(this.legTraining);

        assertThat(this.trainingDao.findTrainingsByMonthAndYearAndUserId(actualMonth, actualYear, userId), is(List.of(this.legTraining)));
    }

    @Test
    void givenExistentIdWhenFindTrainingByIdThenReturnTraining() {
        Long existentId = 1L;

        when(this.trainingRepository.findById(existentId)).thenReturn(Optional.of(this.legTrainingEntity));
        when(this.mapper.toDomain(this.legTrainingEntity)).thenReturn(this.legTraining);

        assertThat(this.trainingDao.findTrainingById(existentId), is(this.legTraining));
    }

    @Test
    void givenNonExistentIdWhenFindTrainingByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.trainingRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.trainingDao.findTrainingById(nonExistentId));
    }

    @Test
    void givenTrainingWhenUpdateTrainingThenReturnTrainingUpdated() {
        Long existentId = 1L;

        when(this.trainingRepository.getReferenceById(existentId)).thenReturn(this.legTrainingEntity);
        when(this.trainingRepository.save(this.legTrainingEntity)).thenReturn(this.legTrainingEntity);
        when(this.mapper.toDomain(this.legTrainingEntity)).thenReturn(this.legTraining);

        assertThat(this.trainingDao.updateTraining(existentId, this.legTraining), is(this.legTraining));
    }

    @Test
    void givenTrainingWhenDeleteTrainingThenTrainingRepositoryDeleteTraining() {
        this.trainingDao.deleteTraining(this.legTraining);

        verify(this.trainingRepository).deleteById(this.legTraining.getId());
    }
}
