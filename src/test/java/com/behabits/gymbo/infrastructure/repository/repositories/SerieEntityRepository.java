package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieEntityRepository {

    public SerieEntity getSquatSerie() {
        return SerieEntity.builder()
                .id(1L)
                .number(1)
                .repetitions(10)
                .weight(100.0)
                .build();
    }
}
