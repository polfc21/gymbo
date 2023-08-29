package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class PublicationResponseRepository {

    private final UserResponse user = new UserResponseRepository().getUserResponse();

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
        LinkResponseRepository linkResponseRepository = new LinkResponseRepository();
        return PublicationResponse.builder()
                .id(1L)
                .description("description")
                .createdAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .updatedAt(LocalDateTime.of(1997, 2, 17, 0, 0))
                .postedBy(this.user)
                .links(List.of(linkResponseRepository.getLinkResponse()))
                .build();
    }

}
