package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.infrastructure.repository.SerieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JpaSerieDaoTest {

    private final Serie squatSerie = new SerieModelRepository().getSquatSerie();

    @InjectMocks
    private JpaSerieDao serieDao;

    @Mock
    private SerieRepository serieRepository;

    @Test
    void givenSerieWhenDeleteSerieThenDeleteSerie() {
        this.serieDao.deleteSerie(this.squatSerie);

        verify(this.serieRepository, times(1)).deleteById(this.squatSerie.getId());
    }

}
