package com.behabits.gymbo.infrastructure.controller.dto.response;

import com.behabits.gymbo.infrastructure.controller.repositories.response.PublicationResponseRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PublicationResponseTest {

    private final PublicationResponseRepository publicationResponseRepository = new PublicationResponseRepository();

    @Test
    void givenSamePublicationResponseWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        PublicationResponse publicationResponse = this.publicationResponseRepository.getPublicationResponse();
        PublicationResponse publicationResponse2 = this.publicationResponseRepository.getPublicationResponse();

        assertThat(publicationResponse, is(publicationResponse2));
        assertThat(publicationResponse.hashCode(), is(publicationResponse2.hashCode()));
        assertThat(publicationResponse.getId(), is(publicationResponse2.getId()));
        assertThat(publicationResponse.getDescription(), is(publicationResponse2.getDescription()));
        assertThat(publicationResponse.getCreatedAt(), is(publicationResponse2.getCreatedAt()));
        assertThat(publicationResponse.getUpdatedAt(), is(publicationResponse2.getUpdatedAt()));
        assertThat(publicationResponse.getPostedBy(), is(publicationResponse2.getPostedBy()));
        assertThat(publicationResponse.getFiles(), is(publicationResponse2.getFiles()));
    }
}
