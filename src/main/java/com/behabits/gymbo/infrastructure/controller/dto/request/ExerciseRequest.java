package com.behabits.gymbo.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRequest {
    private Long id;
    @NotBlank
    private String name;
    private List<SerieRequest> series;
}
