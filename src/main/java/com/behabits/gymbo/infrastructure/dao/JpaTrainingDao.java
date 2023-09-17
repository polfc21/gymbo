package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.TrainingEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaTrainingDao implements TrainingDao {

    private final TrainingRepository trainingRepository;
    private final TrainingEntityMapper mapper;

    @Override
    public List<Training> findTrainingsByMonthAndYearAndUserId(Month month, Year year, Long userId) {
        return this.trainingRepository.findAllByMonthAndYearAndPlayerId(month.getValue(), year.getValue(), userId)
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public Training findTrainingById(Long id) {
        TrainingEntity entity = this.trainingRepository.findById(id)
                .orElse(null);
        return entity != null ? this.mapper.toDomain(entity) : null;
    }

    @Override
    public Training saveTraining(Training training) {
        TrainingEntity entityToCreate = this.mapper.toEntity(training);
        TrainingEntity entityCreated = this.trainingRepository.save(entityToCreate);
        return this.mapper.toDomain(entityCreated);
    }

    @Override
    public void deleteTraining(Training training) {
        this.trainingRepository.deleteById(training.getId());
    }
}
