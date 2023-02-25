package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Serie;
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

    private Serie toDomain(SerieEntity entity) {
        Serie domain = new Serie();
        domain.setId(entity.getId());
        domain.setNumber(entity.getNumber());
        domain.setRepetitions(entity.getRepetitions());
        domain.setWeight(entity.getWeight());
        return domain;
    }

    public List<SerieEntity> toEntity(List<Serie> models) {
        return models != null ? models.stream()
                .map(this::toEntity)
                .toList() : null;
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
