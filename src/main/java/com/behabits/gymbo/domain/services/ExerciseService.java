package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Exercise;

import java.util.List;

public interface ExerciseService {
    Exercise findExerciseById(Long id);
    Exercise createExercise(Exercise exercise);
    List<Exercise> findExercisesByTrainingId(Long trainingId);
    void deleteExercise(Long id);
    Exercise updateExercise(Long id, Exercise exercise);
}
