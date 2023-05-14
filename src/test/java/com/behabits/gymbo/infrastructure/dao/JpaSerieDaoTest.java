package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
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

    private final Serie squatSerie = new SerieModelRepository().getSquatSerie();
    private final SerieEntity squatSerieEntity = new SerieEntityRepository().getSquatSerie();

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
    void givenNonExistentSeriesWhenFindSerieByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.serieRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> this.serieDao.findSerieById(nonExistentId));
    }

    @Test
    void givenSerieWhenUpdateSerieThenReturnSerieUpdated() {
        Long existentId = 1L;

        when(this.serieRepository.getReferenceById(existentId)).thenReturn(this.squatSerieEntity);
        when(this.serieRepository.save(this.squatSerieEntity)).thenReturn(this.squatSerieEntity);
        when(this.mapper.toDomain(this.squatSerieEntity)).thenReturn(this.squatSerie);

        assertThat(this.serieDao.updateSerie(existentId, this.squatSerie), is(this.squatSerie));
    }

}
