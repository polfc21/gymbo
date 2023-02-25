package com.behabits.gymbo.infrastructure.controller.builder.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class TrainingRequestBuilder {

    public TrainingRequest buildCorrectTrainingRequest() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setName("Training 1");
        trainingRequest.setTrainingDate(LocalDateTime.now());
        return trainingRequest;
    }

    public TrainingRequest buildIncorrectTrainingRequest() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setName("");
        trainingRequest.setTrainingDate(null);
        return trainingRequest;
    }

    public TrainingRequest buildNullTrainingRequest() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setName(null);
        trainingRequest.setTrainingDate(null);
        return trainingRequest;
    }

    public TrainingRequest buildLegTrainingRequest() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setName("Leg");
        trainingRequest.setTrainingDate(LocalDateTime.now());
        return trainingRequest;
    }
}
