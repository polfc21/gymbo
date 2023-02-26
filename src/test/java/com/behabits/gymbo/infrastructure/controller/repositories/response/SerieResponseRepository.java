package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieResponseRepository {

    public SerieResponse getSquatSerieResponse() {
        return SerieResponse.builder()
                .id(1L)
                .number(1)
                .repetitions(10)
                .weight(10.0)
                .build();
    }
}
