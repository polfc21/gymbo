package com.behabits.gymbo.infrastructure.repository.mapper;

import com.behabits.gymbo.domain.models.Publication;
import com.behabits.gymbo.domain.repositories.PublicationModelRepository;
import com.behabits.gymbo.infrastructure.repository.entity.PublicationEntity;
import com.behabits.gymbo.infrastructure.repository.repositories.PublicationEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class PublicationEntityMapperTest {

     @Autowired
     private PublicationEntityMapper mapper;

     private final PublicationEntity publicationEntity = new PublicationEntityRepository().getPublication();
     private final Publication publication = new PublicationModelRepository().getPublication();

    @Test
    void givenPublicationWhenMapToEntityThenReturnPublicationEntity() {
        PublicationEntity publicationEntity = this.mapper.toEntity(this.publication);

        assertThat(publicationEntity.getId(), is(this.publication.getId()));
        assertThat(publicationEntity.getDescription(), is(this.publication.getDescription()));
        assertThat(publicationEntity.getCreatedAt(), is(this.publication.getCreatedAt()));
        assertThat(publicationEntity.getUpdatedAt(), is(this.publication.getUpdatedAt()));
        assertThat(publicationEntity.getPlayer().getId(), is(this.publication.getPostedBy().getId()));
    }

    @Test
    void givenPublicationEntityWhenMapToDomainThenReturnPublication() {
        Publication publication = this.mapper.toDomain(this.publicationEntity);

        assertThat(publication.getId(), is(this.publicationEntity.getId()));
        assertThat(publication.getDescription(), is(this.publicationEntity.getDescription()));
        assertThat(publication.getCreatedAt(), is(this.publicationEntity.getCreatedAt()));
        assertThat(publication.getUpdatedAt(), is(this.publicationEntity.getUpdatedAt()));
        assertThat(publication.getPostedBy().getId(), is(this.publicationEntity.getPlayer().getId()));
    }
}
