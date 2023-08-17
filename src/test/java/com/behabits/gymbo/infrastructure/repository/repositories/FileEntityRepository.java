package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.FileEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class FileEntityRepository {

    private final UserEntity player = new UserEntityRepository().getUser();

    public FileEntity getFile() {
        return FileEntity.builder()
                .id(1L)
                .name("file.txt")
                .type("text/plain")
                .data("file".getBytes())
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .player(this.player)
                .build();
    }

}
