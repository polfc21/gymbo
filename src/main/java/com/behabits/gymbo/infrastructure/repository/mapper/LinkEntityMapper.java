package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinkEntityMapper {

    public List<Link> toDomain(List<LinkEntity> entities) {
        return entities != null ? entities.stream()
                .map(this::toDomain)
                .toList() : null;
    }

    public Link toDomain(LinkEntity entity) {
        Link domain = new Link();
        domain.setId(entity.getId());
        domain.setEntity(entity.getEntity());
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
        return entity;
    }

}
