package com.behabits.gymbo.infrastructure.controller.dto.response;

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
}
