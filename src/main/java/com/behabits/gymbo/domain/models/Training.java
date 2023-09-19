package com.behabits.gymbo.domain.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private User user;
    private Sport sport;
    private List<Link> links;

    public void addExercise(Exercise exercise) {
        if (this.exercises == null) {
            this.exercises = List.of(exercise);
        } else {
            List<Exercise> newExercises = new ArrayList<>(this.exercises);
            newExercises.add(exercise);
            this.exercises = List.copyOf(newExercises);
        }
    }

}
