package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Training;
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

    @Override
    public List<Training> findTrainingsByMonthAndYear(Month month, Year year) {
        return this.trainingDao.findTrainingsByMonthAndYear(month, year);
    }

    @Override
    public Training findTrainingById(Long id) {
        return this.trainingDao.findTrainingById(id);
    }

    @Override
    public Training createTraining(Training training) {
        return this.trainingDao.createTraining(training);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        return this.trainingDao.updateTraining(id, training);
    }

    @Override
    public void deleteTraining(Long id) {
        this.trainingDao.deleteTraining(id);
    }

    @Override
    public Training addExercise(Long id, Exercise exercise) {
        Training training = this.trainingDao.findTrainingById(id);
        training.addExercise(exercise);
        return this.trainingDao.createTraining(training);
    }
}
