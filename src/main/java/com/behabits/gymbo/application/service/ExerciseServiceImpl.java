package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.ExerciseDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseDao exerciseDao;
    private final AuthorityService authorityService;

    @Override
    public Exercise findExerciseById(Long id) {
        Exercise exercise = this.exerciseDao.findExerciseById(id);
        if (exercise == null) {
            throw new NotFoundException("Exercise not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(exercise);
        return exercise;
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        User user = this.authorityService.getLoggedUser();
        exercise.setUser(user);
        return this.exerciseDao.saveExercise(exercise);
    }

    @Override
    public List<Exercise> findExercisesByTrainingId(Long trainingId) {
        User user = this.authorityService.getLoggedUser();
        return this.exerciseDao.findExercisesByTrainingIdAndUserId(trainingId, user.getId());
    }

    @Override
    public void deleteExercise(Long id) {
        Exercise exercise = this.findExerciseById(id);
        this.exerciseDao.deleteExercise(exercise);
    }

    @Override
    public Exercise updateExercise(Long id, Exercise exercise) {
        Exercise exerciseToUpdate = this.findExerciseById(id);
        this.authorityService.checkLoggedUserHasPermissions(exerciseToUpdate);
        return this.exerciseDao.updateExercise(id, exercise);
    }
}
