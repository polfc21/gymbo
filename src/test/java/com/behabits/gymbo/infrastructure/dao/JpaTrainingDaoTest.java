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

    private final TrainingEntityRepository trainingEntityRepository = new TrainingEntityRepository();

    private final TrainingModelRepository trainingModelRepository = new TrainingModelRepository();

    @Test
    void givenLegTrainingWhenCreateTrainingThenReturnTraining() {
        Training legTraining = this.trainingModelRepository.getLegTraining();
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTraining();

        when(this.mapper.toEntity(legTraining)).thenReturn(legTrainingEntity);
        when(this.trainingRepository.save(legTrainingEntity)).thenReturn(legTrainingEntity);
        when(this.mapper.toDomain(legTrainingEntity)).thenReturn(legTraining);

        assertThat(this.trainingDao.createTraining(legTraining), is(legTraining));
    }

    @Test
    void givenLegTrainingWithSquatExerciseWhenCreateTrainingThenReturnTraining() {
        Training legTraining = this.trainingModelRepository.getLegTrainingWithSquatExercise();
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTrainingWithSquatExerciseWithSeries();

        when(this.mapper.toEntity(legTraining)).thenReturn(legTrainingEntity);
        when(this.trainingRepository.save(legTrainingEntity)).thenReturn(legTrainingEntity);
        when(this.mapper.toDomain(legTrainingEntity)).thenReturn(legTraining);

        assertThat(this.trainingDao.createTraining(legTraining), is(legTraining));
    }

    @Test
    void givenMonthAndYearAndUserIdWhenFindTrainingsByMonthAndYearAndPlayerIdThenReturnTrainings() {
        Long userId = 1L;
        Training legTraining = this.trainingModelRepository.getLegTraining();
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTraining();
        Month legTrainingMonth = legTraining.getTrainingDate().getMonth();
        Month legTrainingEntityMonth = legTrainingEntity.getTrainingDate().getMonth();
        Year legTrainingYear = Year.of(legTraining.getTrainingDate().getYear());
        Year legTrainingEntityYear = Year.of(legTrainingEntity.getTrainingDate().getYear());

        when(this.trainingRepository.findAllByMonthAndYearAndPlayerId(legTrainingEntityMonth.getValue(), legTrainingEntityYear.getValue(), userId)).thenReturn(List.of(legTrainingEntity));
        when(this.mapper.toDomain(legTrainingEntity)).thenReturn(legTraining);

        assertThat(this.trainingDao.findTrainingsByMonthAndYearAndUserId(legTrainingMonth, legTrainingYear, userId), is(List.of(legTraining)));
    }

    @Test
    void givenExistentIdWhenFindTrainingByIdThenReturnTraining() {
        Training legTraining = this.trainingModelRepository.getLegTraining();
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTraining();

        when(this.trainingRepository.findById(legTrainingEntity.getId())).thenReturn(Optional.of(legTrainingEntity));
        when(this.mapper.toDomain(legTrainingEntity)).thenReturn(legTraining);

        assertThat(this.trainingDao.findTrainingById(legTraining.getId()), is(legTraining));
    }

    @Test
    void givenNonExistentIdWhenFindTrainingByIdThenThrowNotFoundException() {
        when(this.trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.trainingDao.findTrainingById(1L));
    }

    @Test
    void givenTrainingWhenUpdateTrainingThenReturnTrainingUpdated() {
        Long id = 1L;
        Training legTraining = this.trainingModelRepository.getLegTrainingWithSquatExercise();
        TrainingEntity legTrainingEntity = this.trainingEntityRepository.getLegTrainingWithSquatExerciseWithSeries();

        when(this.trainingRepository.getReferenceById(legTraining.getId())).thenReturn(legTrainingEntity);
        when(this.trainingRepository.save(legTrainingEntity)).thenReturn(legTrainingEntity);
        when(this.mapper.toDomain(legTrainingEntity)).thenReturn(legTraining);

        assertThat(this.trainingDao.updateTraining(id, legTraining), is(legTraining));
    }

    @Test
    void givenTrainingWhenDeleteTrainingThenTrainingRepositoryDeleteTraining() {
        Training legTraining = this.trainingModelRepository.getLegTraining();

        this.trainingDao.deleteTraining(legTraining);

        verify(this.trainingRepository).deleteById(legTraining.getId());
    }
}
