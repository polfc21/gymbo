package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Exercise;

public interface ExerciseDao {
    Exercise findExerciseById(Long id);
    Exercise createExercise(Exercise exercise);
}
