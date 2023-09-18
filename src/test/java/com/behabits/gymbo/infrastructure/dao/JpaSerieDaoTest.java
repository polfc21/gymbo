package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.infrastructure.repository.ExerciseRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaSerieDaoTest {

    @InjectMocks
    private JpaSerieDao serieDao;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private SerieEntityMapper mapper;

    @Mock
    private ExerciseRepository exerciseRepository;

    private final Serie squatSerie = new SerieModelRepository().getSquatSerie();
    private final SerieEntity squatSerieEntity = new SerieEntityRepository().getSquatSerie();

    private final ExerciseEntity squatExerciseEntity = new ExerciseEntityRepository().getSquatExerciseWithSeries();
    @Test
    void givenSerieWhenDeleteSerieThenDeleteSerie() {
        this.serieDao.deleteSerie(this.squatSerie);

        verify(this.serieRepository, times(1)).deleteById(this.squatSerie.getId());
    }

    @Test
    void givenExistentSerieWhenFindSerieByIdThenReturnSerie() {
        Long existentId = 1L;

        when(this.serieRepository.findById(existentId)).thenReturn(Optional.of(this.squatSerieEntity));
        when(this.mapper.toDomain(this.squatSerieEntity)).thenReturn(this.squatSerie);

        assertThat(this.serieDao.findSerieById(existentId), is(this.squatSerie));
    }

    @Test
    void givenNonExistentSeriesWhenFindSerieByIdThenReturnNull() {
        Long nonExistentId = 1L;

        when(this.serieRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertNull(this.serieDao.findSerieById(nonExistentId));
    }

    @Test
    void givenSerieWhenUpdateSerieThenReturnSerieUpdated() {
        Long existentId = 1L;

        when(this.serieRepository.getReferenceById(existentId)).thenReturn(this.squatSerieEntity);
        when(this.serieRepository.save(this.squatSerieEntity)).thenReturn(this.squatSerieEntity);
        when(this.mapper.toDomain(this.squatSerieEntity)).thenReturn(this.squatSerie);

        assertThat(this.serieDao.updateSerie(existentId, this.squatSerie), is(this.squatSerie));
    }

    @Test
    void givenExistentExerciseIdWhenFindSeriesByExerciseIdThenReturnSeriesList() {
        Long existentId = 1L;

        when(this.exerciseRepository.getReferenceById(existentId)).thenReturn(this.squatExerciseEntity);
        when(this.mapper.toDomain(this.squatExerciseEntity.getSeries())).thenReturn(List.of(this.squatSerie));

        assertThat(this.serieDao.findSeriesByExerciseId(existentId), is(List.of(this.squatSerie)));
    }

    @Test
    void givenExistentExerciseIdWhenCreateSerieThenReturnSerie() {
        Long existentId = 1L;

        when(this.exerciseRepository.getReferenceById(existentId)).thenReturn(this.squatExerciseEntity);
        when(this.mapper.toEntity(this.squatSerie)).thenReturn(this.squatSerieEntity);
        when(this.serieRepository.save(this.squatSerieEntity)).thenReturn(this.squatSerieEntity);
        when(this.mapper.toDomain(this.squatSerieEntity)).thenReturn(this.squatSerie);

        assertThat(this.serieDao.createSerie(existentId, this.squatSerie), is(this.squatSerie));
        assertThat(this.squatSerieEntity.getExercise(), is(this.squatExerciseEntity));
    }

}
