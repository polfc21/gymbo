package com.behabits.gymbo.domain.repositories;

import com.behabits.gymbo.domain.models.Link;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkModelRepository {

    public Link getLinkWithExercise() {
        return Link.builder()
                .id(1L)
                .entity("EXERCISE")
                .exercise(new ExerciseModelRepository().getSquatExercise())
                .publication(new PublicationModelRepository().getPublication())
                .build();
    }

    public Link getLinkWithUser() {
        return Link.builder()
                .id(1L)
                .entity("USER")
                .user(new UserModelRepository().getUser())
                .publication(new PublicationModelRepository().getPublication())
                .build();
    }

    public Link getLinkWithTraining() {
        return Link.builder()
                .id(1L)
                .entity("TRAINING")
                .training(new TrainingModelRepository().getLegTraining())
                .publication(new PublicationModelRepository().getPublication())
                .build();
    }

}
