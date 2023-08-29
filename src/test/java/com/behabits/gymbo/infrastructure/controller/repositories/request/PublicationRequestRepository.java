package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PublicationRequestRepository {

    public PublicationRequest getPublicationRequest() {
        return PublicationRequest.builder()
                .description("Description")
                .files(List.of(1L))
                .build();
    }

    public PublicationRequest getPublicationRequestWithLinks() {
        LinkRequestRepository linkRequestRepository = new LinkRequestRepository();
        return PublicationRequest.builder()
                .description("Description")
                .files(List.of(1L))
                .links(List.of(linkRequestRepository.getCorrectLinkRequest()))
                .build();
    }

}
