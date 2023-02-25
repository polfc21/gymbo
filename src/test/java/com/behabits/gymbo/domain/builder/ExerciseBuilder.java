package com.behabits.gymbo.domain.builder;

import com.behabits.gymbo.domain.models.Exercise;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExerciseBuilder {

    public Exercise buildSquatExercise() {
        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Squat");
        return exercise;
    }
}
