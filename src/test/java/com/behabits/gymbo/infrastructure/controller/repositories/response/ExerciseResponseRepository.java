package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseResponseRepository {

    public ExerciseResponse getSquatExerciseResponse() {
        return ExerciseResponse.builder()
                .id(1L)
                .name("Squat")
                .series(List.of())
                .build();
    }

    public ExerciseResponse getSquatExerciseResponseWithSeries() {
        SerieResponseRepository serieResponseRepository = new SerieResponseRepository();
        return ExerciseResponse.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieResponseRepository.getSquatSerieResponse()))
                .build();
    }
}
