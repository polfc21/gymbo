package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.FileRequestRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class FileRequestTest {

    private final FileRequestRepository fileRequestRepository = new FileRequestRepository();

    @Test
    void givenSameFileRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        FileRequest fileRequest = this.fileRequestRepository.getCorrectFileRequest();
        FileRequest fileRequest2 = this.fileRequestRepository.getCorrectFileRequest();
        assertThat(fileRequest.getFile().getOriginalFilename(), is(fileRequest2.getFile().getOriginalFilename()));
        assertThat(fileRequest.getFile().getContentType(), is(fileRequest2.getFile().getContentType()));
        assertThat(fileRequest.getFile().getSize(), is(fileRequest2.getFile().getSize()));
        try {
            assertThat(fileRequest.getFile().getBytes(), is(fileRequest2.getFile().getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
