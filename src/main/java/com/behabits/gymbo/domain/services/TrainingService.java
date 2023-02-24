package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Training;

import java.time.Month;
import java.util.List;

public interface TrainingService {
    List<Training> findTrainingsByMonth(Month month);
    Training findTrainingById(Long id);
    Training createTraining(Training training);
}
