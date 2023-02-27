package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerieRequest {
    private Long id;
    @Positive
    @NotNull
    private Integer number;
    @Positive
    @NotNull
    private Integer repetitions;
    @PositiveOrZero
    @NotNull
    private Double weight;
}
