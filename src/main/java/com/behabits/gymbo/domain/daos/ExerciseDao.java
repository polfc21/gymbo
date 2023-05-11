package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;

import java.util.List;

public interface ExerciseDao {
    Exercise findExerciseById(Long id);
    Exercise createExercise(Exercise exercise);
    List<Exercise> findExercisesByTrainingIdAndUserId(Long trainingId, Long userId);
    List<Serie> findSeriesByExerciseId(Long exerciseId);
    Serie createSerie(Long exerciseId, Serie serie);
    void deleteExercise(Long id);
}
