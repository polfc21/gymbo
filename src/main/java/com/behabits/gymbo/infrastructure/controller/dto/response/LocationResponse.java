package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private Long id;
    private String city;
    private String country;
    private String geometry;
}
