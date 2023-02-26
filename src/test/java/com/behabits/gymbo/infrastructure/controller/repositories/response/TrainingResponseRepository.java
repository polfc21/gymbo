package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class TrainingResponseRepository {

    public TrainingResponse getLegTrainingResponse() {
        return TrainingResponse.builder()
                .id(1L)
                .name("Leg")
                .exercises(List.of())
                .build();
    }

    public TrainingResponse getLegTrainingResponseWithSquatExercise() {
        ExerciseResponseRepository exerciseResponseRepository = new ExerciseResponseRepository();
        return TrainingResponse.builder()
                .id(1L)
                .name("Leg")
                .exercises(List.of(exerciseResponseRepository.getSquatExerciseResponse()))
                .build();
    }
}
