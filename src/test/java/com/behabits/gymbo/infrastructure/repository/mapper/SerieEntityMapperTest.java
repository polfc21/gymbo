package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.SerieEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class SerieEntityMapperTest {

    @Autowired
    private SerieEntityMapper mapper;

    private final SerieEntityRepository serieEntityRepository = new SerieEntityRepository();

    private final SerieModelRepository serieModelRepository = new SerieModelRepository();

    @Test
    void givenSerieWhenMapToEntityThenReturnSerieEntity() {
        Serie squatSerie = this.serieModelRepository.getSquatSerie();

        SerieEntity squatSerieEntity = this.mapper.toEntity(squatSerie);

        assertThat(squatSerieEntity.getId(), is(squatSerie.getId()));
        assertThat(squatSerieEntity.getNumber(), is(squatSerie.getNumber()));
        assertThat(squatSerieEntity.getRepetitions(), is(squatSerie.getRepetitions()));
        assertThat(squatSerieEntity.getWeight(), is(squatSerie.getWeight()));
    }

    @Test
    void givenSerieEntityWhenMapToDomainThenReturnSerie() {
        SerieEntity squatSerieEntity = this.serieEntityRepository.getSquatSerie();

        Serie squatSerie = this.mapper.toDomain(squatSerieEntity);

        assertThat(squatSerie.getId(), is(squatSerieEntity.getId()));
        assertThat(squatSerie.getNumber(), is(squatSerieEntity.getNumber()));
        assertThat(squatSerie.getRepetitions(), is(squatSerieEntity.getRepetitions()));
        assertThat(squatSerie.getWeight(), is(squatSerieEntity.getWeight()));
    }
}
