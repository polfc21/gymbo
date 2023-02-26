package com.behabits.gymbo.infrastructure.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponse {
    private Long id;
    private String name;
    private LocalDateTime trainingDate;
    private List<ExerciseResponse> exercises;
}
