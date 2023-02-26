package com.behabits.gymbo.domain.models;

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
public class Training {
    private Long id;
    private LocalDateTime trainingDate;
    private String name;
    private List<Exercise> exercises;
}
