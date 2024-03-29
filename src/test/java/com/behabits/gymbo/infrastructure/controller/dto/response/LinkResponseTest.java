package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.LinkResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LinkResponseTest {

    @Test
    void givenSameLinkResponseWithExerciseWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LinkResponse linkResponse = new LinkResponseRepository().getLinkResponseWithExercise();
        LinkResponse linkResponse2 = new LinkResponseRepository().getLinkResponseWithExercise();
        assertThat(linkResponse, is(linkResponse2));
        assertThat(linkResponse.hashCode(), is(linkResponse2.hashCode()));
    }

    @Test
    void givenSameLinkResponseWithUserWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LinkResponse linkResponse = new LinkResponseRepository().getLinkResponseWithUser();
        LinkResponse linkResponse2 = new LinkResponseRepository().getLinkResponseWithUser();
        assertThat(linkResponse, is(linkResponse2));
        assertThat(linkResponse.hashCode(), is(linkResponse2.hashCode()));
    }

    @Test
    void givenSameLinkResponseWithTrainingWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LinkResponse linkResponse = new LinkResponseRepository().getLinkResponseWithTraining();
        LinkResponse linkResponse2 = new LinkResponseRepository().getLinkResponseWithTraining();
        assertThat(linkResponse, is(linkResponse2));
        assertThat(linkResponse.hashCode(), is(linkResponse2.hashCode()));
    }

}
