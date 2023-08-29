package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.repositories.FileModelRepository;
import com.behabits.gymbo.domain.repositories.PublicationModelRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.PublicationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.PublicationResponse;
import com.behabits.gymbo.infrastructure.controller.repositories.request.PublicationRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class PublicationApiMapperTest {

    @Autowired
    private PublicationApiMapper publicationApiMapper;

    @Test
    void givenPublicationRequestWhenMapToDomainThenReturnPublication() {
        PublicationRequest publicationRequest = new PublicationRequestRepository().getPublicationRequest();

        Publication publication = this.publicationApiMapper.toDomain(publicationRequest);

        assertThat(publication.getDescription(), is(publicationRequest.getDescription()));
        assertThat(publication.getLinks(), is(publicationRequest.getLinks()));
    }

    @Test
    void givenPublicationWhenMapToResponseThenReturnPublicationResponse() {
        Publication publication = new PublicationModelRepository().getPublication();
        publication.setFiles(List.of(new FileModelRepository().getFile()));

        PublicationResponse publicationResponse = this.publicationApiMapper.toResponse(publication);

        assertThat(publicationResponse.getId(), is(publication.getId()));
        assertThat(publicationResponse.getDescription(), is(publication.getDescription()));
        assertThat(publicationResponse.getCreatedAt(), is(publication.getCreatedAt()));
        assertThat(publicationResponse.getUpdatedAt(), is(publication.getUpdatedAt()));
        assertThat(publicationResponse.getPostedBy().getId(), is(publication.getPostedBy().getId()));
        assertThat(publicationResponse.getFiles().size(), is(publication.getFiles().size()));
        assertThat(publicationResponse.getLinks(), is(publication.getLinks()));
    }

}
