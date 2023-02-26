package com.behabits.gymbo.infrastructure.controller.builder.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TrainingResponseBuilder {

    public TrainingResponse buildLegTrainingResponse() {
        TrainingResponse trainingResponse = new TrainingResponse();
        trainingResponse.setId(1L);
        trainingResponse.setName("Leg");
        return trainingResponse;
    }
}