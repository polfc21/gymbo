package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
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

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaExerciseDaoTest {

    @InjectMocks
    private JpaExerciseDao exerciseDao;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseEntityMapper mapper;

    private final ExerciseEntityRepository exerciseEntityRepository = new ExerciseEntityRepository();

    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();

    @Test
    void givenSquatExerciseWhenCreateExerciseThenReturnSquatExercise() {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExercise();
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExercise();

        when(this.mapper.toEntity(squatExercise)).thenReturn(squatExerciseEntity);
        when(this.exerciseRepository.save(squatExerciseEntity)).thenReturn(squatExerciseEntity);
        when(this.mapper.toDomain(squatExerciseEntity)).thenReturn(squatExercise);

        assertThat(this.exerciseDao.createExercise(squatExercise), is(squatExercise));
    }

    @Test
    void givenSquatExerciseWithSeriesWhenCreateExerciseThenReturnSquatExerciseWithSeries() {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExerciseWithSeries();

        when(this.mapper.toEntity(squatExercise)).thenReturn(squatExerciseEntity);
        when(this.exerciseRepository.save(squatExerciseEntity)).thenReturn(squatExerciseEntity);
        when(this.mapper.toDomain(squatExerciseEntity)).thenReturn(squatExercise);

        assertThat(this.exerciseDao.createExercise(squatExercise), is(squatExercise));
    }

    @Test
    void givenExistentIdWhenFindExerciseByIdThenReturnExercise() {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExerciseWithSeries();

        when(this.exerciseRepository.findById(squatExerciseEntity.getId())).thenReturn(Optional.of(squatExerciseEntity));
        when(this.mapper.toDomain(squatExerciseEntity)).thenReturn(squatExercise);

        assertThat(this.exerciseDao.findExerciseById(squatExercise.getId()), is(squatExercise));
    }

    @Test
    void givenNonExistentIdWhenFindExerciseByIdThenThrowNotFoundException() {
        when(this.exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.exerciseDao.findExerciseById(1L));
    }
}
