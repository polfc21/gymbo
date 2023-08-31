package com.behabits.gymbo.infrastructure.controller.repositories.response;

import com.behabits.gymbo.infrastructure.controller.dto.response.LinkResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LinkResponseRepository {

    public LinkResponse getLinkResponse() {
        return LinkResponse.builder()
                .entity("entity")
                .build();
    }

    public LinkResponse getLinkResponseWithExercise() {
        return LinkResponse.builder()
                .entity("exercise")
                .exercise(new ExerciseResponseRepository().getSquatExerciseResponse())
                .build();
    }

}
