package com.behabits.gymbo.domain.daos;

import com.behabits.gymbo.domain.models.Training;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface TrainingDao {
    List<Training> findTrainingsByMonthAndYearAndUserId(Month month, Year year, Long userId);
    Training findTrainingById(Long id);
    Training saveTraining(Training training);
    void deleteTraining(Training training);
}
