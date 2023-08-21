package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.repositories.FileModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.FileEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class FileEntityMapperTest {

    @Autowired
    private FileEntityMapper mapper;

    private final FileEntity fileEntity = new FileEntityRepository().getFile();

    private final File file = new FileModelRepository().getFile();

    @Test
    void givenFileWhenMapToEntityThenReturnFileEntity() {
        FileEntity fileEntity = this.mapper.toEntity(this.file);

        assertThat(fileEntity.getId(), is(this.file.getId()));
        assertThat(fileEntity.getName(), is(this.file.getName()));
        assertThat(fileEntity.getType(), is(this.file.getType()));
        assertThat(fileEntity.getData(), is(this.file.getData()));
        assertThat(fileEntity.getCreatedAt(), is(this.file.getCreatedAt()));
        assertThat(fileEntity.getUpdatedAt(), is(this.file.getUpdatedAt()));
        assertThat(fileEntity.getPlayer().getId(), is(this.file.getUser().getId()));
        assertThat(fileEntity.getPublication().getId(), is(this.file.getPublication().getId()));
    }

    @Test
    void givenFileEntityWhenMapToDomainThenReturnFile() {
        File file = this.mapper.toDomain(this.fileEntity);

        assertThat(file.getId(), is(this.fileEntity.getId()));
        assertThat(file.getName(), is(this.fileEntity.getName()));
        assertThat(file.getType(), is(this.fileEntity.getType()));
        assertThat(file.getData(), is(this.fileEntity.getData()));
        assertThat(file.getCreatedAt(), is(this.fileEntity.getCreatedAt()));
        assertThat(file.getUpdatedAt(), is(this.fileEntity.getUpdatedAt()));
        assertThat(file.getUser().getId(), is(this.fileEntity.getPlayer().getId()));
        assertThat(file.getPublication().getId(), is(this.fileEntity.getPublication().getId()));
    }

}
