package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Exercise;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseModelRepository {

    public Exercise getSquatExercise() {
        return Exercise.builder()
                .id(1L)
                .name("Squat")
                .build();
    }
    
    public Exercise getSquatExerciseWithSquatSeries() {
        SerieModelRepository serieModelRepository = new SerieModelRepository();
        return Exercise.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieModelRepository.getSquatSerie()))
                .build();
    }
}
