package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Exercise;

public interface ExerciseService {
    Exercise findExerciseById(Long id);
    Exercise createExercise(Exercise exercise);
}
