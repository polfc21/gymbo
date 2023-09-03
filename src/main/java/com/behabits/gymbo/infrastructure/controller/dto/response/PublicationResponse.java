package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.domain.models.Sport;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationResponse {
    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse postedBy;
    private List<FileResponse> files;
    private List<LinkResponse> links;
    private Sport sport;
}
