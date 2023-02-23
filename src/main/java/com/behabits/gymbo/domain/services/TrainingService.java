package com.behabits.gymbo.domain.services;

import com.behabits.gymbo.domain.models.Training;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingService {
    List<Training> findTrainingsByMonth(LocalDateTime date);
    Training findTrainingById(Long id);
    Training createTraining(Training training);
}
