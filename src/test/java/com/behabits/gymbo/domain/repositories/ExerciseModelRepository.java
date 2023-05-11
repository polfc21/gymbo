package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ExerciseModelRepository {

    private final User user = new UserModelRepository().getUser();

    public Exercise getSquatExercise() {
        return Exercise.builder()
                .id(1L)
                .name("Squat")
                .user(this.user)
                .build();
    }
    
    public Exercise getSquatExerciseWithSquatSeries() {
        SerieModelRepository serieModelRepository = new SerieModelRepository();
        return Exercise.builder()
                .id(1L)
                .name("Squat")
                .series(List.of(serieModelRepository.getSquatSerie()))
                .user(this.user)
                .build();
    }
}
