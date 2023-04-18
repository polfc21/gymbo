package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.ExerciseModelRepository;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.ExerciseEntityMapper;
import com.behabits.gymbo.infrastructure.repository.mapper.SerieEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.ExerciseEntityRepository;
import com.behabits.gymbo.infrastructure.repository.repositories.SerieEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    private SerieRepository serieRepository;

    @Mock
    private ExerciseEntityMapper mapper;

    @Mock
    private SerieEntityMapper serieMapper;

    private final ExerciseEntityRepository exerciseEntityRepository = new ExerciseEntityRepository();
    private final ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();
    private final SerieEntityRepository serieEntityRepository = new SerieEntityRepository();
    private final SerieModelRepository serieModelRepository = new SerieModelRepository();

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

    @Test
    void givenExerciseWithTrainingIdWhenFindExercisesByTrainingIdThenReturnExerciseList() {
        Long trainingId = 1L;
        Exercise squatExercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExerciseWithSeries();

        when(this.exerciseRepository.findAllByTrainingId(trainingId)).thenReturn(List.of(squatExerciseEntity));
        when(this.mapper.toDomain(List.of(squatExerciseEntity))).thenReturn(List.of(squatExercise));

        assertThat(this.exerciseDao.findExercisesByTrainingId(trainingId), is(List.of(squatExercise)));
    }

    @Test
    void givenNonExistentExerciseIdWhenFindSeriesByExerciseIdThenThrowNotFoundException() {
        when(this.exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.exerciseDao.findSeriesByExerciseId(1L));
    }

    @Test
    void givenExistentExerciseIdWhenFindSeriesByExerciseIdThenReturnSeriesList() {
        Exercise squatExercise = this.exerciseModelRepository.getSquatExerciseWithSquatSeries();
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExerciseWithSeries();

        when(this.exerciseRepository.findById(squatExerciseEntity.getId())).thenReturn(Optional.of(squatExerciseEntity));
        when(this.mapper.toDomain(squatExerciseEntity)).thenReturn(squatExercise);

        assertThat(this.exerciseDao.findSeriesByExerciseId(squatExercise.getId()), is(squatExercise.getSeries()));
    }

    @Test
    void givenExistentExerciseIdWhenCreateSerieThenReturnSerie() {
        Long existentId = 1L;
        ExerciseEntity squatExerciseEntity = this.exerciseEntityRepository.getSquatExerciseWithSeries();
        Serie squatSerie = this.serieModelRepository.getSquatSerie();
        SerieEntity squatSerieEntity = this.serieEntityRepository.getSquatSerie();

        when(this.exerciseRepository.findById(existentId)).thenReturn(Optional.of(squatExerciseEntity));
        when(this.serieMapper.toEntity(squatSerie)).thenReturn(squatSerieEntity);
        when(this.serieRepository.save(squatSerieEntity)).thenReturn(squatSerieEntity);
        when(this.serieMapper.toDomain(squatSerieEntity)).thenReturn(squatSerie);

        assertThat(this.exerciseDao.createSerie(existentId, squatSerie), is(squatSerie));
        assertThat(squatSerieEntity.getExercise(), is(squatExerciseEntity));
    }

    @Test
    void givenNonExistentExerciseIdWhenCreateSerieThenThrowNotFoundException() {
        when(this.exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.exerciseDao.findSeriesByExerciseId(1L));
    }
}
