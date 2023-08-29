package com.behabits.gymbo.infrastructure.controller.dto.request;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {
    private String entity;
}
