package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;

import java.util.List;

public interface ExerciseService {
    Exercise findExerciseById(Long id);
    Exercise createExercise(Exercise exercise);
    List<Exercise> findExercisesByTrainingId(Long trainingId);
    List<Serie> findSeriesByExerciseId(Long exerciseId);
    Serie createSerie(Long exerciseId, Serie serie);
    void deleteExercise(Long id);
}
