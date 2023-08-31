package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class PublicationResponseRepository {

    private final UserResponse user = new UserResponseRepository().getUserResponse();
    private final LinkResponseRepository linkResponseRepository = new LinkResponseRepository();

    public PublicationResponse getPublicationResponse() {
        return PublicationResponse.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .build();
    }

    public PublicationResponse getPublicationResponseWithLinks() {
        return PublicationResponse.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(this.linkResponseRepository.getLinkResponse()))
                .build();
    }

    public PublicationResponse getPublicationResponseWithExerciseLink() {
        return PublicationResponse.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(this.linkResponseRepository.getLinkResponseWithExercise()))
                .build();
    }

}
