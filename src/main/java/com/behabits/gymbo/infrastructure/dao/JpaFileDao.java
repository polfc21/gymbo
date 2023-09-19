package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.FileDao;
import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.infrastructure.repository.FileRepository;
import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.FileEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaFileDao implements FileDao {

    private final FileRepository fileRepository;
    private final FileEntityMapper fileEntityMapper;

    @Override
    public File saveFile(File file) {
        FileEntity entityToCreate = this.fileEntityMapper.toEntity(file);
        FileEntity entityCreated = this.fileRepository.save(entityToCreate);
        return this.fileEntityMapper.toDomain(entityCreated);
    }

    @Override
    public File findFileById(Long id) {
        FileEntity fileEntity = this.fileRepository.findById(id)
                .orElse(null);
        return fileEntity != null ? this.fileEntityMapper.toDomain(fileEntity) : null;
    }

    @Override
    public void deleteFile(File file) {
        this.fileRepository.deleteById(file.getId());
    }

}
