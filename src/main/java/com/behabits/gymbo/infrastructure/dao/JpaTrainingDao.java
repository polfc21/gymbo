package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
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
                .orElseThrow(() -> new NotFoundException("Training with " + id + " not found"));
        return this.mapper.toDomain(entity);
    }

    @Override
    public Training createTraining(Training training) {
        TrainingEntity entity = this.mapper.toEntity(training);
        entity = this.trainingRepository.save(entity);
        return this.mapper.toDomain(entity);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        TrainingEntity trainingEntity = this.trainingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training with " + id + " not found"));
        trainingEntity.setName(training.getName());
        trainingEntity.setTrainingDate(training.getTrainingDate());
        trainingEntity = this.trainingRepository.save(trainingEntity);
        return this.mapper.toDomain(trainingEntity);
    }

    @Override
    public void deleteTraining(Long id) {
        TrainingEntity trainingEntity = this.trainingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training with " + id + " not found"));
        this.trainingRepository.delete(trainingEntity);
    }
}
