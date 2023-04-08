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
    public List<Training> findTrainingsByMonthAndYear(Month month, Year year) {
        return this.trainingRepository.findAllByMonthAndYear(month.getValue(), year.getValue()).stream()
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
    public Training updateTraining(Training training) {
        TrainingEntity trainingEntity = this.trainingRepository.findById(training.getId())
                .orElseThrow(() -> new NotFoundException("Training with " + training.getId() + " not found"));
        trainingEntity.setName(training.getName());
        trainingEntity.setTrainingDate(training.getTrainingDate());
        trainingEntity = this.trainingRepository.save(trainingEntity);
        return this.mapper.toDomain(trainingEntity);
    }
}
