package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.repositories.SerieModelRepository;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.controller.builder.request.SerieRequestBuilder;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class SerieApiMapperTest {

    @Autowired
    private SerieApiMapper serieApiMapper;

    private SerieRequestBuilder serieRequestBuilder;

    private SerieModelRepository serieModelRepository;

    @BeforeEach
    void setUp() {
        this.serieRequestBuilder = new SerieRequestBuilder();
        this.serieModelRepository = new SerieModelRepository();
    }

    @Test
    void givenSquatSerieRequestWhenMapToDomainThenReturnSquatSerie() {
        SerieRequest serieRequest = this.serieRequestBuilder.buildSquatSerieRequest();

        Serie serie = this.serieApiMapper.toDomain(serieRequest);

        assertThat(serie.getId(), is(serieRequest.getId()));
        assertThat(serie.getNumber(), is(serieRequest.getNumber()));
        assertThat(serie.getRepetitions(), is(serieRequest.getRepetitions()));
        assertThat(serie.getWeight(), is(serieRequest.getWeight()));
    }

    @Test
    void givenSquatSerieWhenMapToResponseThenReturnSquatSerieResponse() {
        Serie serie = this.serieModelRepository.buildSquatSerie();

        SerieResponse serieResponse = this.serieApiMapper.toResponse(serie);

        assertThat(serieResponse.getId(), is(serie.getId()));
        assertThat(serieResponse.getNumber(), is(serie.getNumber()));
        assertThat(serieResponse.getRepetitions(), is(serie.getRepetitions()));
        assertThat(serieResponse.getWeight(), is(serie.getWeight()));
    }
}
