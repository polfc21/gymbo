package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerieRequest {
    private Long id;
    @Positive
    private Integer number;
    @Positive
    private Integer repetitions;
    @PositiveOrZero
    private Double weight;
}
