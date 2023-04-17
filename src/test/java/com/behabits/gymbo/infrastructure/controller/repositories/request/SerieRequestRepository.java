package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieRequestRepository {

    public SerieRequest getCorrectSerieRequest() {
        return SerieRequest.builder()
                .number(1)
                .repetitions(10)
                .weight(10.0)
                .build();
    }

    public SerieRequest getIncorrectSerieRequest() {
        return SerieRequest.builder()
                .number(0)
                .repetitions(0)
                .weight(-1.0)
                .build();
    }

    public SerieRequest getNullSerieRequest() {
        return SerieRequest.builder()
                .number(null)
                .repetitions(null)
                .weight(null)
                .build();
    }

    public SerieRequest getSquatSerieRequest() {
        return SerieRequest.builder()
                .number(1)
                .repetitions(10)
                .weight(10.0)
                .build();
    }

}
