package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class PublicationEntityRepository {

    private final UserEntity player = new UserEntityRepository().getUser();

    public PublicationEntity getPublication() {
        return PublicationEntity.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .player(this.player)
                .build();
    }

    public PublicationEntity getPublicationWithLink() {
        LinkEntity link = new LinkEntityRepository().getLink();
        return PublicationEntity.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .player(this.player)
                .links(List.of(link))
                .build();
    }

}
