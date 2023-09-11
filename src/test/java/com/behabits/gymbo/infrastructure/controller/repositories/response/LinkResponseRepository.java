package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.LinkResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkResponseRepository {

    public LinkResponse getLinkResponseWithExercise() {
        return LinkResponse.builder()
                .entity("EXERCISE")
                .exercise(new ExerciseResponseRepository().getSquatExerciseResponse())
                .build();
    }

    public LinkResponse getLinkResponseWithUser() {
        return LinkResponse.builder()
                .entity("USER")
                .user(new UserResponseRepository().getUserResponse())
                .build();
    }

    public LinkResponse getLinkResponseWithTraining() {
        return LinkResponse.builder()
                .entity("TRAINING")
                .training(new TrainingResponseRepository().getLegTrainingResponse())
                .build();
    }

}
