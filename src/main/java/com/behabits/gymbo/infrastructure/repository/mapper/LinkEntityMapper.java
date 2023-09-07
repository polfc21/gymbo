package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkEntityMapper {

    private final ExerciseEntityMapper exerciseEntityMapper;
    private final UserEntityMapper userEntityMapper;

    public List<Link> toDomain(List<LinkEntity> entities) {
        return entities != null ? entities.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Link toDomain(LinkEntity entity) {
        Link domain = new Link();
        domain.setId(entity.getId());
        domain.setEntity(entity.getEntity());
        if (entity.getExercise() != null) {
            domain.setExercise(this.exerciseEntityMapper.toDomain(entity.getExercise()));
        }
        if (entity.getPlayer() != null) {
            domain.setUser(this.userEntityMapper.toDomain(entity.getPlayer()));
        }
        return domain;
    }

    public List<LinkEntity> toEntity(List<Link> models) {
        return models != null ? models.stream()
                .map(this::toEntity)
                .toList() : null;
    }

    public LinkEntity toEntity(Link domain) {
        LinkEntity entity = new LinkEntity();
        entity.setId(domain.getId());
        entity.setEntity(domain.getEntity());
        if (domain.getExercise() != null) {
            entity.setExercise(this.exerciseEntityMapper.toEntity(domain.getExercise()));
        }
        if (domain.getUser() != null) {
            entity.setPlayer(this.userEntityMapper.toEntity(domain.getUser()));
        }
        return entity;
    }

}
