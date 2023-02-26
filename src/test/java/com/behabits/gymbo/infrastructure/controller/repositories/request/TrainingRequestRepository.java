package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class TrainingRequestRepository {

    public TrainingRequest getCorrectTrainingRequest() {
        return TrainingRequest.builder()
                .id(null)
                .name("Training 1")
                .trainingDate(LocalDateTime.now())
                .build();
    }

    public TrainingRequest getIncorrectTrainingRequest() {
        return TrainingRequest.builder()
                .id(1L)
                .name("")
                .trainingDate(null)
                .build();
    }

    public TrainingRequest getNullTrainingRequest() {
        return TrainingRequest.builder()
                .id(null)
                .name(null)
                .trainingDate(null)
                .build();
    }

    public TrainingRequest getLegTrainingRequest() {
        return TrainingRequest.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .build();
    }

    public TrainingRequest getLegTrainingRequestWithSquatExercise() {
        ExerciseRequestRepository exerciseRequestRepository = new ExerciseRequestRepository();
        return TrainingRequest.builder()
                .id(1L)
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .exercises(List.of(exerciseRequestRepository.getSquatExerciseRequest()))
                .build();
    }
}
