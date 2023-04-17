package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Training;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface TrainingService {
    List<Training> findTrainingsByMonthAndYear(Month month, Year year);
    Training findTrainingById(Long id);
    Training createTraining(Training training);
    Training updateTraining(Long id, Training training);
    void deleteTraining(Long id);
}
