package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Link;
import com.behabits.gymbo.domain.repositories.LinkModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.LinkEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.LinkEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class LinkEntityMapperTest {

    @Autowired
    private LinkEntityMapper mapper;

    private final LinkEntityRepository linkEntityRepository = new LinkEntityRepository();
    private final LinkModelRepository linkModelRepository = new LinkModelRepository();

    @Test
    void givenLinkWhenMapToEntityThenReturnLinkEntity() {
        Link link = this.linkModelRepository.getLink();
        LinkEntity linkEntity = this.mapper.toEntity(link);

        assertThat(linkEntity.getId(), is(link.getId()));
        assertThat(linkEntity.getEntity(), is(link.getEntity()));
    }

    @Test
    void givenLinkEntityWhenMapToDomainThenReturnLink() {
        LinkEntity linkEntity = this.linkEntityRepository.getLink();
        Link link = this.mapper.toDomain(linkEntity);

        assertThat(link.getId(), is(linkEntity.getId()));
        assertThat(link.getEntity(), is(linkEntity.getEntity()));
    }

    @Test
    void givenLinkWithExerciseWhenMapToEntityThenReturnLinkEntityWithExercise() {
        Link link = this.linkModelRepository.getLinkWithExercise();
        LinkEntity linkEntity = this.mapper.toEntity(link);

        assertThat(linkEntity.getId(), is(link.getId()));
        assertThat(linkEntity.getEntity(), is(link.getEntity()));
        assertThat(linkEntity.getExercise().getId(), is(link.getExercise().getId()));
    }

    @Test
    void givenLinkEntityWithExerciseWhenMapToDomainThenReturnLinkWithExercise() {
        LinkEntity linkEntity = this.linkEntityRepository.getLinkWithExercise();
        Link link = this.mapper.toDomain(linkEntity);

        assertThat(link.getId(), is(linkEntity.getId()));
        assertThat(link.getEntity(), is(linkEntity.getEntity()));
        assertThat(link.getExercise().getId(), is(linkEntity.getExercise().getId()));
    }

}
