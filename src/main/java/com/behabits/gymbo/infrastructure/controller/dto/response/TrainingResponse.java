package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.domain.models.Sport;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingResponse {
    private Long id;
    private String name;
    private LocalDateTime trainingDate;
    private List<ExerciseResponse> exercises;
    private Sport sport;
}
