package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.LinkResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LinkResponseTest {

    @Test
    void givenSameLinkResponseWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        LinkResponse linkResponse = new LinkResponseRepository().getLinkResponse();
        LinkResponse linkResponse2 = new LinkResponseRepository().getLinkResponse();
        assertThat(linkResponse, is(linkResponse2));
        assertThat(linkResponse.hashCode(), is(linkResponse2.hashCode()));
    }

}
