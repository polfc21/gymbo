package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.domain.models.Training;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class TrainingModelRepository {

    public Training getLegTraining() {
        return Training.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .sport(Sport.FOOTBALL)
                .build();
    }

    public Training getLegTrainingWithSquatExercise() {
        ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();
        return Training.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .exercises(List.of(exerciseModelRepository.getSquatExerciseWithSquatSeries()))
                .sport(Sport.FOOTBALL)
                .build();
    }
}
