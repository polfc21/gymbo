package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Exercise;

import java.util.List;

public interface ExerciseDao {
    Exercise findExerciseById(Long id);
    Exercise saveExercise(Exercise exercise);
    List<Exercise> findExercisesByTrainingIdAndUserId(Long trainingId, Long userId);
    void deleteExercise(Exercise exercise);
}
