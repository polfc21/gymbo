package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerieResponse {
    private Long id;
    private Integer number;
    private Integer repetitions;
    private Double weight;
}
