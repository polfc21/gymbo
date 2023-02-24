package com.behabits.gymbo.application.service;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {

    //private final TrainingDao trainingDao;

    @Override
    public List<Training> findTrainingsByMonth(LocalDateTime date) {
        return null;
        //return this.trainingDao.findTrainingsByMonth(date);
    }

    @Override
    public Training findTrainingById(Long id) {
        return null;
        //return this.trainingDao.findTrainingById(id);
    }

    @Override
    public Training createTraining(Training training) {
        return null;
        //return this.trainingDao.createTraining(training);
    }
}
