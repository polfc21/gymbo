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
                .publication(new PublicationEntityRepository().getPublication())
                .build();
    }

    public LinkEntity getLinkWithUser() {
        return LinkEntity.builder()
                .id(1L)
                .entity("USER")
                .player(new UserEntityRepository().getUser())
                .publication(new PublicationEntityRepository().getPublication())
                .build();
    }

    public LinkEntity getLinkWithTraining() {
        return LinkEntity.builder()
                .id(1L)
                .entity("TRAINING")
                .training(new TrainingEntityRepository().getLegTraining())
                .publication(new PublicationEntityRepository().getPublication())
                .build();
    }

}
