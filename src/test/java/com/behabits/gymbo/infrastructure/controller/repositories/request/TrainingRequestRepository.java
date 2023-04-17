package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class TrainingRequestRepository {

    public TrainingRequest getCorrectTrainingRequest() {
        return TrainingRequest.builder()
                .name("Training 1")
                .trainingDate(LocalDateTime.now())
                .build();
    }

    public TrainingRequest getIncorrectTrainingRequest() {
        return TrainingRequest.builder()
                .name("")
                .trainingDate(null)
                .build();
    }

    public TrainingRequest getNullTrainingRequest() {
        return TrainingRequest.builder()
                .name(null)
                .trainingDate(null)
                .build();
    }

    public TrainingRequest getLegTrainingRequest() {
        return TrainingRequest.builder()
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .build();
    }

    public TrainingRequest getLegTrainingRequestWithSquatExercise() {
        ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();
        return TrainingRequest.builder()
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .exercises(List.of(exerciseRequestRepository.getSquatExerciseRequest()))
                .build();
    }
}
