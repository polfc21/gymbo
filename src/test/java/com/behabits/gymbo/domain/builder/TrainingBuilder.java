package com.behabits.gymbo.domain.builder;

import com.behabits.gymbo.domain.models.Training;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TrainingBuilder {

    public Training buildLegTraining() {
        Training training = new Training();
        training.setId(1L);
        training.setName("Leg");
        return training;
    }
}
