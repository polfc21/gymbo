package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.repositories.FileModelRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.FileRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.FileResponse;
import com.behabits.gymbo.infrastructure.controller.repositories.request.FileRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class FileApiMapperTest {

    @Autowired
    private FileApiMapper fileApiMapper;

    private final FileRequestRepository fileRequestRepository = new FileRequestRepository();

    private final FileModelRepository fileModelRepository = new FileModelRepository();

    @Test
    void givenFileRequestWhenMapToDomainThenReturnFile() {
        FileRequest fileRequest = this.fileRequestRepository.getCorrectFileRequest();

        File file;
        try {
            file = this.fileApiMapper.toDomain(fileRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(file.getName(), is(fileRequest.getFile().getOriginalFilename()));
        assertThat(file.getType(), is(fileRequest.getFile().getContentType()));
        try {
            assertThat(file.getData(), is(fileRequest.getFile().getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void givenFileWhenMapToResponseThenReturnFileResponse() {
        File file = this.fileModelRepository.getFile();

        FileResponse fileResponse = this.fileApiMapper.toResponse(file);

        assertThat(fileResponse.getId(), is(file.getId()));
        assertThat(fileResponse.getName(), is(file.getName()));
        assertThat(fileResponse.getType(), is(file.getType()));
        assertThat(fileResponse.getData(), is(file.getData()));
        assertThat(fileResponse.getCreatedAt(), is(file.getCreatedAt()));
        assertThat(fileResponse.getUpdatedAt(), is(file.getUpdatedAt()));
    }
}
