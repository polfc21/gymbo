package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.models.Sport;
import com.behabits.gymbo.domain.models.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class PublicationModelRepository {

    private final User user = new UserModelRepository().getUser();
    private final LinkModelRepository linkModelRepository = new LinkModelRepository();

    public Publication getPublication() {
        return Publication.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .sport(Sport.FOOTBALL)
                .build();
    }

    public Publication getPublicationWithLinkExercise() {
        Link link = this.linkModelRepository.getLinkWithExercise();
        return Publication.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(link))
                .sport(Sport.FOOTBALL)
                .build();
    }

    public Publication getPublicationWithExerciseLink() {
        Link link = this.linkModelRepository.getLinkWithExercise();
        return Publication.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(link))
                .sport(Sport.FOOTBALL)
                .build();
    }

    public Publication getPublicationWithUserLink() {
        Link link = this.linkModelRepository.getLinkWithUser();
        return Publication.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(link))
                .sport(Sport.FOOTBALL)
                .build();
    }

}
