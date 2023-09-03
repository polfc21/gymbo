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
                .sport("FOOTBALL")
                .build();
    }

    public TrainingRequest getIncorrectTrainingRequest() {
        return TrainingRequest.builder()
                .name("")
                .trainingDate(null)
                .sport("")
                .exercises(List.of(new ExerciseRequestRepository().getIncorrectExerciseRequest()))
                .build();
    }

    public TrainingRequest getNullTrainingRequest() {
        return TrainingRequest.builder()
                .name(null)
                .trainingDate(null)
                .exercises(List.of(new ExerciseRequestRepository().getNullExerciseRequest()))
                .sport(null)
                .build();
    }

    public TrainingRequest getLegTrainingRequest() {
        return TrainingRequest.builder()
                .name("Leg")
                .trainingDate(LocalDateTime.now())
                .sport("FOOTBALL")
                .build();
    }

}
