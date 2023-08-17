package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.File;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class FileModelRepository {

    private final User user = new UserModelRepository().getUser();

    public File getFile() {
        return File.builder()
                .id(1L)
                .name("file.txt")
                .type("text/plain")
                .data("file".getBytes())
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .user(this.user)
                .build();
    }
}
