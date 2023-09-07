package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.dto.validator.ValidLink;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {
    @ValidLink
    private String entity;
    private Long exerciseId;
    private String username;
}
