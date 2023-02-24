package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingEntityMapper {

    private final ExerciseEntityMapper exerciseEntityMapper;

    public Training toDomain(TrainingEntity entity) {
        Training domain = new Training();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDate(entity.getDate());
        domain.setExercises(this.exerciseEntityMapper.toDomain(entity.getExercises()));
        return domain;
    }

    public TrainingEntity toEntity(Training domain) {
        TrainingEntity entity = new TrainingEntity();
        entity.setId(domain.getId());
        entity.setName(entity.getName());
        entity.setExercises(this.exerciseEntityMapper.toEntity(domain.getExercises()));
        entity.getExercises().forEach(exerciseEntity -> exerciseEntity.setTraining(entity));
        return entity;
    }
}
