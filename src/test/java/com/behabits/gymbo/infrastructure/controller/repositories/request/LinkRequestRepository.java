package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.LinkRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkRequestRepository {

    public LinkRequest getIncorrectLinkRequest() {
        return LinkRequest.builder()
                .entity("incorrect")
                .build();
    }

    public LinkRequest getLinkWithExerciseRequest() {
        return LinkRequest.builder()
                .entity("EXERCISE")
                .exerciseId(1L)
                .build();
    }

    public LinkRequest getLinkWithUserRequest() {
        return LinkRequest.builder()
                .entity("USER")
                .username("username")
                .build();
    }

}
