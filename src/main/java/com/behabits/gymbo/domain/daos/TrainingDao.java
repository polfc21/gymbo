package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Training;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingDao {
    List<Training> findTrainingsByMonth(LocalDateTime date);
    Training findTrainingById(Long id);
    Training createTraining(Training training);
}
