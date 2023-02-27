package com.behabits.gymbo.domain.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    private Long id;
    private LocalDateTime trainingDate;
    private String name;
    private List<Exercise> exercises;
}
