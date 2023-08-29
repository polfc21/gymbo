package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class PublicationModelRepository {

    private final User user = new UserModelRepository().getUser();

    public Publication getPublication() {
        return Publication.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .build();
    }

    public Publication getPublicationWithLink() {
        Link link = new LinkModelRepository().getLink();
        return Publication.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(link))
                .build();
    }

}
