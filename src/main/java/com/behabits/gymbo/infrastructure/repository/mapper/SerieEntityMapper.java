package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SerieEntityMapper {

    public List<Serie> toDomain(List<SerieEntity> entities) {
        return entities != null ? entities.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Serie toDomain(SerieEntity entity) {
        Serie domain = new Serie();
        domain.setId(entity.getId());
        domain.setNumber(entity.getNumber());
        domain.setRepetitions(entity.getRepetitions());
        domain.setWeight(entity.getWeight());
        Exercise exercise = new Exercise();
        exercise.setId(entity.getExercise().getId());
        domain.setExercise(exercise);
        return domain;
    }

    public List<SerieEntity> toEntity(List<Serie> models) {
        return models != null ? models.stream()
                .map(this::toEntity)
                .toList() : null;
    }

    public SerieEntity toEntity(Serie domain) {
        SerieEntity entity = new SerieEntity();
        entity.setId(domain.getId());
        entity.setNumber(domain.getNumber());
        entity.setRepetitions(domain.getRepetitions());
        entity.setWeight(domain.getWeight());
        if (domain.getExercise() != null) {
            ExerciseEntity exerciseEntity = new ExerciseEntity();
            exerciseEntity.setId(domain.getExercise().getId());
            entity.setExercise(exerciseEntity);
        }
        return entity;
    }
}
