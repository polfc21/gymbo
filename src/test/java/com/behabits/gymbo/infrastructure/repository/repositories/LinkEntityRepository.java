package com.behabits.gymbo.infrastructure.repository.repositories;

import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkEntityRepository {

    public LinkEntity getLinkWithExercise() {
        return LinkEntity.builder()
                .id(1L)
                .entity("EXERCISE")
                .exercise(new ExerciseEntityRepository().getSquatExercise())
                .build();
    }

    public LinkEntity getLinkWithUser() {
        return LinkEntity.builder()
                .id(1L)
                .entity("USER")
                .player(new UserEntityRepository().getUser())
                .build();
    }

}
