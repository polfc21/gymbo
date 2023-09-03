package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.domain.models.Sport;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResponse {
    private Long id;
    private String name;
    private List<SerieResponse> series;
    private Sport sport;
}
