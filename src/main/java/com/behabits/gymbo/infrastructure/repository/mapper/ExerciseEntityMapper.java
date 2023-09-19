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
    private final UserEntityMapper userEntityMapper;
    private final LinkEntityMapper linkEntityMapper;

    public List<Exercise> toDomain(List<ExerciseEntity> entities) {
        return entities != null ? entities.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Exercise toDomain(ExerciseEntity entity) {
        Exercise domain = new Exercise();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setSeries(this.serieEntityMapper.toDomain(entity.getSeries()));
        domain.setUser(this.userEntityMapper.toDomain(entity.getPlayer()));
        domain.setSport(entity.getSport());
        domain.setLinks(this.linkEntityMapper.toDomain(entity.getLinks()));
        return domain;
    }

    public List<ExerciseEntity> toEntity(List<Exercise> models) {
        return models != null ? models.stream()
                .map(this::toEntity)
                .toList() : null;
    }

    public ExerciseEntity toEntity(Exercise domain) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setSeries(this.serieEntityMapper.toEntity(domain.getSeries()));
        if (entity.getSeries() != null) {
            entity.getSeries().forEach(serieEntity -> serieEntity.setExercise(entity));
        }
        entity.setPlayer(this.userEntityMapper.toEntity(domain.getUser()));
        entity.setSport(domain.getSport());
        if (domain.getLinks() != null) {
            entity.setLinks(this.linkEntityMapper.toEntity(domain.getLinks()));
            entity.getLinks().forEach(linkEntity -> linkEntity.setExercise(entity));
        }
        return entity;
    }

}
