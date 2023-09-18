package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.domain.services.AuthorityService;
import com.behabits.gymbo.domain.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDao trainingDao;
    private final AuthorityService authorityService;

    @Override
    public List<Training> findTrainingsByMonthAndYear(Month month, Year year) {
        Long loggedUserId = this.authorityService.getLoggedUser().getId();
        return this.trainingDao.findTrainingsByMonthAndYearAndUserId(month, year, loggedUserId);
    }

    @Override
    public Training findTrainingById(Long id) {
        Training training = this.trainingDao.findTrainingById(id);
        if (training == null) {
            throw new NotFoundException("Training not found");
        }
        this.authorityService.checkLoggedUserHasPermissions(training);
        return training;
    }

    @Override
    public Training createTraining(Training training) {
        User loggedUser = this.authorityService.getLoggedUser();
        training.setUser(loggedUser);
        return this.trainingDao.saveTraining(training);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        Training trainingToUpdate = this.findTrainingById(id);
        trainingToUpdate.setName(training.getName());
        trainingToUpdate.setTrainingDate(training.getTrainingDate());
        return this.trainingDao.saveTraining(trainingToUpdate);
    }

    @Override
    public void deleteTraining(Long id) {
        Training training = this.findTrainingById(id);
        this.authorityService.checkLoggedUserHasPermissions(training);
        this.trainingDao.deleteTraining(training);
    }

    @Override
    public Training addExercise(Long id, Exercise exercise) {
        Training training = this.findTrainingById(id);
        training.addExercise(exercise);
        return this.trainingDao.saveTraining(training);
    }
}
