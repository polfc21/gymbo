package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Training;

import java.time.Month;
import java.util.List;

public interface TrainingDao {
    List<Training> findTrainingsByMonth(Month month);
    Training findTrainingById(Long id);
    Training createTraining(Training training);
}
