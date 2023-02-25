package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.infrastructure.repository.entity.ExerciseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseEntityMapper {

    private final SerieEntityMapper serieEntityMapper;

    public List<Exercise> toDomain(List<ExerciseEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

    public Exercise toDomain(ExerciseEntity entity) {
        Exercise domain = new Exercise();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setSeries(this.serieEntityMapper.toDomain(entity.getSeries()));
        return domain;
    }

    public List<ExerciseEntity> toEntity(List<Exercise> models) {
        return models.stream()
                .map(this::toEntity)
                .toList();
    }

    public ExerciseEntity toEntity(Exercise domain) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setSeries(this.serieEntityMapper.toEntity(domain.getSeries()));
        entity.getSeries().forEach(serieEntity -> serieEntity.setExercise(entity));
        return entity;
    }

}
