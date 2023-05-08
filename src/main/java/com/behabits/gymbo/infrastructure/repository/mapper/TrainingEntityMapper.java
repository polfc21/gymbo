package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.infrastructure.repository.entity.TrainingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingEntityMapper {

    private final ExerciseEntityMapper exerciseEntityMapper;
    private final UserEntityMapper userEntityMapper;

    public Training toDomain(TrainingEntity entity) {
        Training domain = new Training();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setTrainingDate(entity.getTrainingDate());
        domain.setExercises(this.exerciseEntityMapper.toDomain(entity.getExercises()));
        if (entity.getPlayer() != null) {
            domain.setUser(this.userEntityMapper.toDomain(entity.getPlayer()));
        }
        return domain;
    }

    public TrainingEntity toEntity(Training domain) {
        TrainingEntity entity = new TrainingEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setTrainingDate(domain.getTrainingDate());
        entity.setExercises(this.exerciseEntityMapper.toEntity(domain.getExercises()));
        if (entity.getExercises() != null) {
            entity.getExercises().forEach(exerciseEntity -> exerciseEntity.setTraining(entity));
        }
        if (domain.getUser() != null) {
            entity.setPlayer(this.userEntityMapper.toEntity(domain.getUser()));
        }
        return entity;
    }
}
