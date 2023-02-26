package com.behabits.gymbo.infrastructure.controller.builder.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExerciseRequestBuilder {

    public ExerciseRequest buildCorrectExerciseRequest() {
        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("Bench Press");
        return exerciseRequest;
    }

    public ExerciseRequest buildIncorrectExerciseRequest() {
        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("");
        return exerciseRequest;
    }

    public ExerciseRequest buildNullExerciseRequest() {
        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName(null);
        return exerciseRequest;
    }

    public ExerciseRequest buildSquatExerciseRequest() {
        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setId(1L);
        exerciseRequest.setName("Squat");
        return exerciseRequest;
    }

}