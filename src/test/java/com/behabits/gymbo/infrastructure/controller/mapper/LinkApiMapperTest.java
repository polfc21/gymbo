package com.behabits.gymbo.infrastructure.controller.mapper;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.repositories.LinkModelRepository;
import com.behabits.gymbo.infrastructure.controller.dto.request.LinkRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LinkResponse;
import com.behabits.gymbo.infrastructure.controller.repositories.request.LinkRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class LinkApiMapperTest {

    @Autowired
    private LinkApiMapper linkApiMapper;

    @Test
    void givenLinkRequestWhenMapToDomainThenReturnLink() {
        LinkRequest linkRequest = new LinkRequestRepository().getCorrectLinkRequest();

        Link link = this.linkApiMapper.toDomain(linkRequest);

        assertThat(link.getEntity(), is(linkRequest.getEntity()));
    }

    @Test
    void givenLinkWhenMapToResponseThenReturnLinkResponse() {
        Link link = new LinkModelRepository().getLink();

        LinkResponse linkResponse = this.linkApiMapper.toResponse(link);

        assertThat(linkResponse.getId(), is(link.getId()));
        assertThat(linkResponse.getEntity(), is(link.getEntity()));
    }

}
