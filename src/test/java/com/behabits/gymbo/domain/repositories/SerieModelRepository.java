package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Serie;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieModelRepository {

    public Serie getSquatSerie() {
        return Serie.builder()
                .id(1L)
                .number(1)
                .repetitions(10)
                .weight(100.0)
                .build();
    }
}
