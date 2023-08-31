package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkEntityRepository {

    public LinkEntity getLink() {
        return LinkEntity.builder()
                .id(1L)
                .entity("entity")
                .build();
    }

    public LinkEntity getLinkWithExercise() {
        return LinkEntity.builder()
                .id(1L)
                .entity("entity")
                .exercise(new ExerciseEntityRepository().getSquatExercise())
                .build();
    }

}
