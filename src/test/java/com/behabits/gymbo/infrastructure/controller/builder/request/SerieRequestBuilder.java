package com.behabits.gymbo.infrastructure.controller.builder.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SerieRequestBuilder {

    public SerieRequest buildCorrectSerieRequest() {
        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setNumber(1);
        serieRequest.setRepetitions(10);
        serieRequest.setWeight(10.0);
        return serieRequest;
    }

    public SerieRequest buildIncorrectSerieRequest() {
        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setNumber(0);
        serieRequest.setRepetitions(0);
        serieRequest.setWeight(-0.01);
        return serieRequest;
    }

    public SerieRequest buildNullSerieRequest() {
        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setNumber(null);
        serieRequest.setRepetitions(null);
        serieRequest.setWeight(null);
        return serieRequest;
    }

    public SerieRequest buildSquatSerieRequest() {
        SerieRequest serieRequest = new SerieRequest();
        serieRequest.setId(1L);
        serieRequest.setNumber(1);
        serieRequest.setRepetitions(10);
        serieRequest.setWeight(10.0);
        return serieRequest;
    }

}
