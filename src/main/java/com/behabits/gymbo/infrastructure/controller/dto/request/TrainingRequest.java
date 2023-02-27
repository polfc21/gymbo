package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequest {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime trainingDate;
    @Valid
    private List<ExerciseRequest> exercises;
}
