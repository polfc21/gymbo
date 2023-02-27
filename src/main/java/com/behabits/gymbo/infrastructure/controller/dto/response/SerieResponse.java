package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerieResponse {
    private Long id;
    private Integer number;
    private Integer repetitions;
    private Double weight;
}
