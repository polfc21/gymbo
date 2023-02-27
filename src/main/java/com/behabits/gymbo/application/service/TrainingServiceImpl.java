package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
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
}
