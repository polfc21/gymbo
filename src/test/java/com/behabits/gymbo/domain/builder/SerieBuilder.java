package com.behabits.gymbo.domain.builder;

import com.behabits.gymbo.domain.models.Serie;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieBuilder {

    public Serie buildSquatSerie() {
        Serie serie = new Serie();
        serie.setId(1L);
        serie.setRepetitions(10);
        serie.setWeight(10.0);
        return serie;
    }
}
