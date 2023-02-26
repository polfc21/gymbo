package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Training;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class TrainingModelRepository {

    public Training buildLegTraining() {
        Training training = new Training();
        training.setId(1L);
        training.setName("Leg");
        training.setTrainingDate(LocalDateTime.now());
        return training;
    }

    public Training buildLegTrainingWithSquatExercise() {
        ExerciseModelRepository exerciseModelRepository = new ExerciseModelRepository();
        return Training.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .exercises(List.of(exerciseModelRepository.buildSquatExerciseWithSquatSeries()))
                .build();
    }
}
