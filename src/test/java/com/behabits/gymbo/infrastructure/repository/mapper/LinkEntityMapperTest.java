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

    private final LinkEntity linkEntity = new LinkEntityRepository().getLink();
    private final Link link = new LinkModelRepository().getLink();

    @Test
    void givenLinkWhenMapToEntityThenReturnLinkEntity() {
        LinkEntity linkEntity = this.mapper.toEntity(this.link);

        assertThat(linkEntity.getId(), is(this.link.getId()));
        assertThat(linkEntity.getEntity(), is(this.link.getEntity()));
    }

    @Test
    void givenLinkEntityWhenMapToDomainThenReturnLink() {
        Link link = this.mapper.toDomain(this.linkEntity);

        assertThat(link.getId(), is(this.linkEntity.getId()));
        assertThat(link.getEntity(), is(this.linkEntity.getEntity()));
    }

}
