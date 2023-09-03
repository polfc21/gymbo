package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseRequestRepository {

    public ExerciseRequest getCorrectExerciseRequest() {
        return ExerciseRequest.builder()
                .name("Bench Press")
                .sport("FOOTBALL")
                .build();
    }

    public ExerciseRequest getIncorrectExerciseRequest() {
        return ExerciseRequest.builder()
                .name("")
                .sport("")
                .build();
    }

    public ExerciseRequest getNullExerciseRequest() {
        return ExerciseRequest.builder()
                .name(null)
                .build();
    }

    public ExerciseRequest getSquatExerciseRequest() {
        return ExerciseRequest.builder()
                .name("Squat")
                .sport("FOOTBALL")
                .build();
    }

    public ExerciseRequest getSquatExerciseRequestWithSeries() {
        SerieRequestRepository serieRequestRepository = new SerieRequestRepository();
        return ExerciseRequest.builder()
                .name("Squat")
                .series(List.of(serieRequestRepository.getSquatSerieRequest()))
                .build();
    }

}
