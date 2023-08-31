package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.LinkRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LinkRequestTest {

    @Test
    void givenSameLinkRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LinkRequest linkRequest = new LinkRequestRepository().getCorrectLinkRequest();
        LinkRequest linkRequest2 = new LinkRequestRepository().getCorrectLinkRequest();
        assertThat(linkRequest, is(linkRequest2));
        assertThat(linkRequest.hashCode(), is(linkRequest2.hashCode()));
    }

    @Test
    void givenSameLinkRequestWithExerciseWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LinkRequest linkRequest = new LinkRequestRepository().getLinkWithExerciseRequest();
        LinkRequest linkRequest2 = new LinkRequestRepository().getLinkWithExerciseRequest();
        assertThat(linkRequest, is(linkRequest2));
        assertThat(linkRequest.hashCode(), is(linkRequest2.hashCode()));
    }

}
