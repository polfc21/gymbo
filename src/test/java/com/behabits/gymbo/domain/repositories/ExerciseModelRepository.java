package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Exercise;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseModelRepository {

    public Exercise buildSquatExercise() {
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Squat");
        return exercise;
    }
    
    public Exercise buildSquatExerciseWithSquatSeries() {
        SerieModelRepository serieModelRepository = new SerieModelRepository();
        return Exercise.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieModelRepository.buildSquatSerie()))
                .build();
    }
}
