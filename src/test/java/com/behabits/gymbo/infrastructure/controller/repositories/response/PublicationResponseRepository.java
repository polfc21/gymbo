package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.UserResponse;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
