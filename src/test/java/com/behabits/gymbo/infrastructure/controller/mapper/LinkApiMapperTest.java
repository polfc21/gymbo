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

    private final LinkModelRepository linkModelRepository = new LinkModelRepository();
    private final LinkRequestRepository linkRequestRepository = new LinkRequestRepository();

    @Test
    void givenLinkRequestWithExerciseWhenMapToDomainThenReturnLinkWithExercise() {
        LinkRequest linkRequest = this.linkRequestRepository.getLinkWithExerciseRequest();

        Link link = this.linkApiMapper.toDomain(linkRequest);

        assertThat(link.getEntity(), is(linkRequest.getEntity()));
        assertThat(link.getExercise().getId(), is(linkRequest.getExerciseId()));
    }

    @Test
    void givenLinkWithExerciseWhenMapToResponseThenReturnLinkResponseWithExercise() {
        Link link = this.linkModelRepository.getLinkWithExercise();

        LinkResponse linkResponse = this.linkApiMapper.toResponse(link);

        assertThat(linkResponse.getId(), is(link.getId()));
        assertThat(linkResponse.getEntity(), is(link.getEntity()));
        assertThat(linkResponse.getExercise().getId(), is(link.getExercise().getId()));
    }

    @Test
    void givenLinkRequestWithUserWhenMapToDomainThenReturnLinkWithUser() {
        LinkRequest linkRequest = this.linkRequestRepository.getLinkWithUserRequest();

        Link link = this.linkApiMapper.toDomain(linkRequest);

        assertThat(link.getEntity(), is(linkRequest.getEntity()));
        assertThat(link.getUser().getUsername(), is(linkRequest.getUsername()));
    }

    @Test
    void givenLinkWithUserWhenMapToResponseThenReturnLinkResponseWithUser() {
        Link link = this.linkModelRepository.getLinkWithUser();

        LinkResponse linkResponse = this.linkApiMapper.toResponse(link);

        assertThat(linkResponse.getId(), is(link.getId()));
        assertThat(linkResponse.getEntity(), is(link.getEntity()));
        assertThat(linkResponse.getUser().getUsername(), is(link.getUser().getUsername()));
    }

    @Test
    void givenLinkRequestWithTrainingWhenMapToDomainThenReturnLinkWithTraining() {
        LinkRequest linkRequest = this.linkRequestRepository.getLinkWithTrainingRequest();

        Link link = this.linkApiMapper.toDomain(linkRequest);

        assertThat(link.getEntity(), is(linkRequest.getEntity()));
        assertThat(link.getTraining().getId(), is(linkRequest.getTrainingId()));
    }

    @Test
    void givenLinkWithTrainingWhenMapToResponseThenReturnLinkResponseWithTraining() {
        Link link = this.linkModelRepository.getLinkWithTraining();

        LinkResponse linkResponse = this.linkApiMapper.toResponse(link);

        assertThat(linkResponse.getId(), is(link.getId()));
        assertThat(linkResponse.getEntity(), is(link.getEntity()));
        assertThat(linkResponse.getTraining().getId(), is(link.getTraining().getId()));
    }

}
