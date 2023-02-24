package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.TrainingDao;
import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.infrastructure.repository.TrainingRepository;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.TrainingEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaTrainingDao implements TrainingDao {

    private final TrainingRepository trainingRepository;
    private final TrainingEntityMapper mapper;

    @Override
    public List<Training> findTrainingsByMonth(Month month) {
        return this.trainingRepository.findAllByMonth(month).stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Training findTrainingById(Long id) {
        TrainingEntity entity = this.trainingRepository.findById(id)
                .orElse(null);
        return this.mapper.toDomain(entity);
    }

    @Override
    public Training createTraining(Training training) {
        TrainingEntity entity = this.mapper.toEntity(training);
        entity = this.trainingRepository.save(entity);
        return this.mapper.toDomain(entity);
    }
}
