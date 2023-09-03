package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class TrainingEntityRepository {

    public TrainingEntity getLegTraining() {
        return TrainingEntity.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .sport(Sport.FOOTBALL)
                .build();
    }

    public TrainingEntity getLegTrainingWithSquatExerciseWithSeries() {
        ExerciseEntityRepository exerciseEntityRepository = new ExerciseEntityRepository();
        return TrainingEntity.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .exercises(List.of(exerciseEntityRepository.getSquatExerciseWithSeries()))
                .sport(Sport.FOOTBALL)
                .build();
    }

}
