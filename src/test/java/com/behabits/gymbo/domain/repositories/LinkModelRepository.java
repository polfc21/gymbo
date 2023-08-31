package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Link;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkModelRepository {

    public Link getLink() {
        return Link.builder()
                .id(1L)
                .entity("entity")
                .build();
    }

    public Link getLinkWithExercise() {
        return Link.builder()
                .id(1L)
                .entity("entity")
                .exercise(new ExerciseModelRepository().getSquatExercise())
                .build();
    }

}
