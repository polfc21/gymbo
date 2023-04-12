package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseDao exerciseDao;

    @Override
    public Exercise findExerciseById(Long id) {
        return this.exerciseDao.findExerciseById(id);
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        return this.exerciseDao.createExercise(exercise);
    }

    @Override
    public List<Exercise> findExercisesByTrainingId(Long trainingId) {
        return this.exerciseDao.findExercisesByTrainingId(trainingId);
    }

    @Override
    public List<Serie> findSeriesByExerciseId(Long exerciseId) {
        return this.exerciseDao.findSeriesByExerciseId(exerciseId);
    }
}
