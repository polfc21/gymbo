package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.daos.FileDao;
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
        FileEntity entityToSave = this.fileEntityMapper.toEntity(file);
        FileEntity entitySaved = this.fileRepository.save(entityToSave);
        return this.fileEntityMapper.toDomain(entitySaved);
    }

    @Override
    public File findFileById(Long id) {
        FileEntity entity = this.fileRepository.findById(id)
                .orElse(null);
        return entity != null ? this.fileEntityMapper.toDomain(entity) : null;
    }

    @Override
    public void deleteFile(File file) {
        this.fileRepository.deleteById(file.getId());
    }

}
