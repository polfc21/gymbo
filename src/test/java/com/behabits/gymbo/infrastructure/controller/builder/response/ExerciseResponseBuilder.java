package com.behabits.gymbo.infrastructure.controller.builder.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExerciseResponseBuilder {

    public ExerciseResponse buildSquatExerciseResponse() {
        ExerciseResponse exerciseResponse = new ExerciseResponse();
        exerciseResponse.setId(1L);
        exerciseResponse.setName("Squat");
        return exerciseResponse;
    }
}
