package com.behabits.gymbo.infrastructure.controller.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationRequest {
    private String description;
    private List<Long> files;
}
