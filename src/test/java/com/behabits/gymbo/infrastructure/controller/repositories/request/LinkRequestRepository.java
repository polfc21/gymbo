package com.behabits.gymbo.infrastructure.controller.repositories.request;

import com.behabits.gymbo.infrastructure.controller.dto.request.LinkRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkRequestRepository {

    public LinkRequest getCorrectLinkRequest() {
        return LinkRequest.builder()
                .entity("entity")
                .build();
    }

}
