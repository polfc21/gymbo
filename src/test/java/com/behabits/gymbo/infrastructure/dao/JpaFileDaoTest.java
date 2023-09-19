package com.behabits.gymbo.infrastructure.dao;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.repositories.FileModelRepository;
import com.behabits.gymbo.infrastructure.repository.FileRepository;
import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import com.behabits.gymbo.infrastructure.repository.mapper.FileEntityMapper;
import com.behabits.gymbo.infrastructure.repository.repositories.FileEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaFileDaoTest{

    @InjectMocks
    private JpaFileDao fileDao;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileEntityMapper fileEntityMapper;

    private final File file = new FileModelRepository().getFile();
    private final FileEntity fileEntity = new FileEntityRepository().getFile();

    @Test
    void givenFileWhenCreateFileThenReturnFile() {
        when(this.fileEntityMapper.toEntity(this.file)).thenReturn(this.fileEntity);
        when(this.fileRepository.save(this.fileEntity)).thenReturn(this.fileEntity);
        when(this.fileEntityMapper.toDomain(this.fileEntity)).thenReturn(this.file);

        assertThat(this.fileDao.saveFile(this.file), is(this.file));
    }

    @Test
    void givenExistentIdWhenFindFileByIdThenReturnFile() {
        Long existentId = 1L;

        when(this.fileRepository.findById(existentId)).thenReturn(Optional.of(this.fileEntity));
        when(this.fileEntityMapper.toDomain(this.fileEntity)).thenReturn(this.file);

        assertThat(this.fileDao.findFileById(existentId), is(this.file));
    }

    @Test
    void givenNonExistentIdWhenFindFileByIdThenThrowNotFoundException() {
        Long nonExistentId = 1L;

        when(this.fileRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertNull(this.fileDao.findFileById(nonExistentId));
    }

    @Test
    void givenFileWhenDeleteFileThenVerifyDeleteFileById() {
        this.fileDao.deleteFile(this.file);

        verify(this.fileRepository).deleteById(this.file.getId());
    }

}
