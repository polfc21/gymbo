package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.SerieEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.SerieEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaSerieDaoTest {

    @InjectMocks
    private JpaSerieDao serieDao;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private SerieEntityMapper mapper;

    private final Serie squatSerie = new SerieModelRepository().getSquatSerie();
    private final SerieEntity squatSerieEntity = new SerieEntityRepository().getSquatSerie();

    @Test
    void givenSerieWhenSaveSerieThenReturnSerie() {
        when(this.mapper.toEntity(this.squatSerie)).thenReturn(this.squatSerieEntity);
        when(this.serieRepository.save(this.squatSerieEntity)).thenReturn(this.squatSerieEntity);
        when(this.mapper.toDomain(this.squatSerieEntity)).thenReturn(this.squatSerie);

        assertThat(this.serieDao.saveSerie(this.squatSerie), is(this.squatSerie));
    }

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

}
