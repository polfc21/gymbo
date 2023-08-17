package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.FileResponse;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class FileResponseRepository {

    public FileResponse getFileResponse() {
        return FileResponse.builder()
                .id(1L)
                .name("file.txt")
                .type("text/plain")
                .data("file".getBytes())
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .build();
    }

}
