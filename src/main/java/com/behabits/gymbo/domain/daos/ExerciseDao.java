package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Exercise;

import java.util.List;

public interface ExerciseDao {
    Exercise findExerciseById(Long id);
    Exercise createExercise(Exercise exercise);
    List<Exercise> findExercisesByTrainingId(Long trainingId);
}
