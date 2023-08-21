package com.behabits.gymbo.infrastructure.controller.dto.request;

import com.behabits.gymbo.infrastructure.controller.repositories.request.PublicationRequestRepository;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PublicationRequestTest {

    private final PublicationRequestRepository publicationRequestRepository = new PublicationRequestRepository();

    @Test
    void givenSamePublicationRequestWhenEqualsAndHashCodeThenReturnTrueSameHashCode() {
        PublicationRequest publicationRequest = this.publicationRequestRepository.getPublicationRequest();
        PublicationRequest publicationRequest2 = this.publicationRequestRepository.getPublicationRequest();

        assertThat(publicationRequest, is(publicationRequest2));
        assertThat(publicationRequest.hashCode(), is(publicationRequest2.hashCode()));
        assertThat(publicationRequest.getDescription(), is(publicationRequest2.getDescription()));
        assertThat(publicationRequest.getFiles(), is(publicationRequest2.getFiles()));
    }
}
