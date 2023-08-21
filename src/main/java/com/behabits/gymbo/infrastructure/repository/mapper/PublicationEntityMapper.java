package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicationEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public Publication toDomain(PublicationEntity entity) {
        Publication domain = new Publication();
        domain.setId(entity.getId());
        domain.setDescription(entity.getDescription());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setPostedBy(this.userEntityMapper.toDomain(entity.getPlayer()));
        return domain;
    }

    public PublicationEntity toEntity(Publication domain) {
        PublicationEntity entity = new PublicationEntity();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setPlayer(this.userEntityMapper.toEntity(domain.getPostedBy()));
        return entity;
    }
}
