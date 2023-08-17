package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.FileResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
class FileResponseTest {

    private final FileResponseRepository fileResponseRepository = new FileResponseRepository();

    @Test
    void givenSameFileResponseWhenEqualsAndHashCodeThenReturnTrueAndSameHashCode() {
        FileResponse fileResponse = this.fileResponseRepository.getFileResponse();
        FileResponse fileResponse2 = this.fileResponseRepository.getFileResponse();

        assertThat(fileResponse.getId(), is(fileResponse2.getId()));
        assertThat(fileResponse.getName(), is(fileResponse2.getName()));
        assertThat(fileResponse.getType(), is(fileResponse2.getType()));
        assertThat(fileResponse.getData(), is(fileResponse2.getData()));
        assertThat(fileResponse.getCreatedAt(), is(fileResponse2.getCreatedAt()));
        assertThat(fileResponse.getUpdatedAt(), is(fileResponse2.getUpdatedAt()));
    }
}
