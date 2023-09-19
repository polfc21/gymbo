package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.User;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final LinkEntityMapper linkEntityMapper;

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User domain = new User();
        domain.setId(entity.getId());
        domain.setUsername(entity.getUsername());
        domain.setFirstName(entity.getFirstName());
        domain.setLastName(entity.getLastName());
        domain.setEmail(entity.getEmail());
        domain.setPassword(entity.getPassword());
        domain.setSports(entity.getSports());
        domain.setLinks(this.linkEntityMapper.toDomain(entity.getLinks()));
        return domain;
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setFirstName(domain.getFirstName());
        entity.setLastName(domain.getLastName());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setSports(domain.getSports());
        if (domain.getLinks() != null) {
            entity.setLinks(this.linkEntityMapper.toEntity(domain.getLinks()));
            entity.getLinks().forEach(linkEntity -> linkEntity.setPlayer(entity));
        }
        return entity;
    }
}
