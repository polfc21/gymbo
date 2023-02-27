package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRequest {
    private Long id;
    @NotBlank
    private String name;
    @Valid
    private List<SerieRequest> series;
}
