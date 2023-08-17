package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileEntityMapper {

    private final UserEntityMapper userEntityMapper;

    public File toDomain(FileEntity entity) {
        File domain = new File();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setType(entity.getType());
        domain.setData(entity.getData());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setUser(this.userEntityMapper.toDomain(entity.getPlayer()));
        return domain;
    }

    public FileEntity toEntity(File domain) {
        FileEntity entity = new FileEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setType(domain.getType());
        entity.setData(domain.getData());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setPlayer(this.userEntityMapper.toEntity(domain.getUser()));
        return entity;
    }

}
