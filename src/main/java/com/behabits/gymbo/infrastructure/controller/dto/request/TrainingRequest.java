package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequest {
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime trainingDate;
    private List<ExerciseRequest> exercises;
}
