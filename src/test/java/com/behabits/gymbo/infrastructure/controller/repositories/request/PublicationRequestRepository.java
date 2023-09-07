package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PublicationRequestRepository {

    private final LinkRequestRepository linkRequestRepository = new LinkRequestRepository();

    public PublicationRequest getPublicationRequest() {
        return PublicationRequest.builder()
                .description("Description")
                .files(List.of(1L))
                .sport("FOOTBALL")
                .build();
    }

    public PublicationRequest getPublicationRequestWithExerciseLink() {
        return PublicationRequest.builder()
                .description("Description")
                .files(List.of(1L))
                .links(List.of(this.linkRequestRepository.getLinkWithExerciseRequest()))
                .sport("FOOTBALL")
                .build();
    }

    public PublicationRequest getPublicationRequestWithUserLink() {
        return PublicationRequest.builder()
                .description("Description")
                .files(List.of(1L))
                .links(List.of(this.linkRequestRepository.getLinkWithUserRequest()))
                .sport("FOOTBALL")
                .build();
    }

}
