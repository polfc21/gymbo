package com.behabits.gymbo.infrastructure.controller.builder.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieResponseBuilder {

    public SerieResponse buildSquatSerieResponse() {
        SerieResponse serieResponse = new SerieResponse();
        serieResponse.setNumber(1);
        serieResponse.setRepetitions(10);
        serieResponse.setWeight(10.0);
        return serieResponse;
    }
}
