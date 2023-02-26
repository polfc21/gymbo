package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseRequestRepository {

    public ExerciseRequest getCorrectExerciseRequest() {
        return ExerciseRequest.builder()
                .id(null)
                .name("Bench Press")
                .build();
    }

    public ExerciseRequest getIncorrectExerciseRequest() {
        return ExerciseRequest.builder()
                .id(1L)
                .name("")
                .build();
    }

    public ExerciseRequest getNullExerciseRequest() {
        return ExerciseRequest.builder()
                .id(null)
                .name(null)
                .build();
    }

    public ExerciseRequest getSquatExerciseRequest() {
        return ExerciseRequest.builder()
                .id(1L)
                .name("Squat")
                .build();
    }

    public ExerciseRequest getSquatExerciseRequestWithSeries() {
        SerieRequestRepository serieRequestRepository = new SerieRequestRepository();
        return ExerciseRequest.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieRequestRepository.getSquatSerieRequest()))
                .build();
    }

}
