package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ExerciseEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.ExerciseEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaExerciseDaoTest {

    @InjectMocks
    private JpaExerciseDao exerciseDao;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseEntityMapper mapper;

    private final Exercise squatExercise = new ExerciseModelRepository().getSquatExercise();
    private final ExerciseEntity squatExerciseEntity = new ExerciseEntityRepository().getSquatExercise();

    @Test
    void givenSquatExerciseWhenCreateExerciseThenReturnSquatExercise() {
        when(this.mapper.toEntity(this.squatExercise)).thenReturn(this.squatExerciseEntity);
        when(this.exerciseRepository.save(this.squatExerciseEntity)).thenReturn(this.squatExerciseEntity);
        when(this.mapper.toDomain(this.squatExerciseEntity)).thenReturn(this.squatExercise);

        assertThat(this.exerciseDao.createExercise(this.squatExercise), is(this.squatExercise));
    }

    @Test
    void givenSquatExerciseWithSeriesWhenCreateExerciseThenReturnSquatExerciseWithSeries() {
        when(this.mapper.toEntity(this.squatExercise)).thenReturn(this.squatExerciseEntity);
        when(this.exerciseRepository.save(this.squatExerciseEntity)).thenReturn(this.squatExerciseEntity);
        when(this.mapper.toDomain(this.squatExerciseEntity)).thenReturn(this.squatExercise);

        assertThat(this.exerciseDao.createExercise(this.squatExercise), is(this.squatExercise));
    }

    @Test
    void givenExistentIdWhenFindExerciseByIdThenReturnExercise() {
        Long existentId = 1L;

        when(this.exerciseRepository.findById(existentId)).thenReturn(Optional.of(this.squatExerciseEntity));
        when(this.mapper.toDomain(this.squatExerciseEntity)).thenReturn(this.squatExercise);

        assertThat(this.exerciseDao.findExerciseById(existentId), is(this.squatExercise));
    }

    @Test
    void givenNonExistentIdWhenFindExerciseByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.exerciseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertNull(this.exerciseDao.findExerciseById(nonExistentId));
    }

    @Test
    void givenExerciseWithTrainingIdAndUserIdWhenFindExercisesByTrainingIdThenReturnExerciseList() {
        Long trainingId = 1L;
        Long userId = 1L;

        when(this.exerciseRepository.findAllByTrainingIdAndPlayerId(trainingId, userId)).thenReturn(List.of(this.squatExerciseEntity));
        when(this.mapper.toDomain(List.of(this.squatExerciseEntity))).thenReturn(List.of(this.squatExercise));

        assertThat(this.exerciseDao.findExercisesByTrainingIdAndUserId(trainingId, userId), is(List.of(this.squatExercise)));
    }

    @Test
    void givenExerciseWhenDeleteExerciseThenDeleteExercise() {
        this.exerciseDao.deleteExercise(this.squatExercise);

        verify(this.exerciseRepository).deleteById(this.squatExercise.getId());
    }

    @Test
    void givenExerciseWhenUpdateExerciseThenReturnExerciseUpdated() {
        Long existentId = 1L;

        when(this.exerciseRepository.getReferenceById(existentId)).thenReturn(this.squatExerciseEntity);
        when(this.exerciseRepository.save(this.squatExerciseEntity)).thenReturn(this.squatExerciseEntity);
        when(this.mapper.toDomain(squatExerciseEntity)).thenReturn(squatExercise);

        assertThat(this.exerciseDao.updateExercise(existentId, this.squatExercise), is(this.squatExercise));
    }
}
