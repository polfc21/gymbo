package com.behabits.gymbo.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    private LocalDateTime date;
    private String name;
    private List<Exercise> exercises;
}
