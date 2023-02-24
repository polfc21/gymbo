package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.infrastructure.repository.entity.SerieEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SerieEntityMapper {

    public List<Serie> toDomain(List<SerieEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Serie toDomain(SerieEntity entity) {
        Serie domain = new Serie();
        domain.setId(entity.getId());
        domain.setNumber(entity.getNumber());
        domain.setRepetitions(entity.getRepetitions());
        domain.setWeight(entity.getWeight());
        return domain;
    }

    public List<SerieEntity> toEntity(List<Serie> models) {
        return models.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    private SerieEntity toEntity(Serie domain) {
        SerieEntity entity = new SerieEntity();
        entity.setId(domain.getId());
        entity.setNumber(domain.getNumber());
        entity.setRepetitions(domain.getRepetitions());
        entity.setWeight(domain.getWeight());
        return entity;
    }
}
