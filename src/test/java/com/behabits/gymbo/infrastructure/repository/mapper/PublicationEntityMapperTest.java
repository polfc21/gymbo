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

     private final PublicationEntityRepository publicationEntityRepository = new PublicationEntityRepository();
     private final PublicationModelRepository publicationModelRepository = new PublicationModelRepository();

    @Test
    void givenPublicationWhenMapToEntityThenReturnPublicationEntity() {
        Publication publication = this.publicationModelRepository.getPublication();

        PublicationEntity publicationEntity = this.mapper.toEntity(publication);

        assertThat(publicationEntity.getId(), is(publication.getId()));
        assertThat(publicationEntity.getDescription(), is(publication.getDescription()));
        assertThat(publicationEntity.getCreatedAt(), is(publication.getCreatedAt()));
        assertThat(publicationEntity.getUpdatedAt(), is(publication.getUpdatedAt()));
        assertThat(publicationEntity.getPlayer().getId(), is(publication.getPostedBy().getId()));
        assertThat(publicationEntity.getSport(), is(publication.getSport()));
    }

    @Test
    void givenPublicationEntityWhenMapToDomainThenReturnPublication() {
        PublicationEntity publicationEntity = this.publicationEntityRepository.getPublication();

        Publication publication = this.mapper.toDomain(publicationEntity);

        assertThat(publication.getId(), is(publicationEntity.getId()));
        assertThat(publication.getDescription(), is(publicationEntity.getDescription()));
        assertThat(publication.getCreatedAt(), is(publicationEntity.getCreatedAt()));
        assertThat(publication.getUpdatedAt(), is(publicationEntity.getUpdatedAt()));
        assertThat(publication.getPostedBy().getId(), is(publicationEntity.getPlayer().getId()));
        assertThat(publication.getSport(), is(publicationEntity.getSport()));
    }

    @Test
    void givenPublicationWithLinksWhenMapToEntityThenReturnPublicationEntityWithLinks() {
        Publication publication = this.publicationModelRepository.getPublicationWithLink();

        PublicationEntity publicationEntity = this.mapper.toEntity(publication);

        assertThat(publicationEntity.getId(), is(publication.getId()));
        assertThat(publicationEntity.getDescription(), is(publication.getDescription()));
        assertThat(publicationEntity.getCreatedAt(), is(publication.getCreatedAt()));
        assertThat(publicationEntity.getUpdatedAt(), is(publication.getUpdatedAt()));
        assertThat(publicationEntity.getPlayer().getId(), is(publication.getPostedBy().getId()));
        assertThat(publicationEntity.getSport(), is(publication.getSport()));

        assertThat(publicationEntity.getLinks().get(0).getId(),
                is(publication.getLinks().get(0).getId()));
        assertThat(publicationEntity.getLinks().get(0).getEntity(),
                is(publication.getLinks().get(0).getEntity()));
    }

    @Test
    void givenPublicationEntityWithLinksWhenMapToDomainThenReturnPublicationWithLinks() {
        PublicationEntity publicationEntity = this.publicationEntityRepository.getPublicationWithLink();

        Publication publication = this.mapper.toDomain(publicationEntity);

        assertThat(publication.getId(), is(publicationEntity.getId()));
        assertThat(publication.getDescription(), is(publicationEntity.getDescription()));
        assertThat(publication.getCreatedAt(), is(publicationEntity.getCreatedAt()));
        assertThat(publication.getUpdatedAt(), is(publicationEntity.getUpdatedAt()));
        assertThat(publication.getPostedBy().getId(), is(publicationEntity.getPlayer().getId()));
        assertThat(publication.getSport(), is(publicationEntity.getSport()));

        assertThat(publication.getLinks().get(0).getId(),
                is(publicationEntity.getLinks().get(0).getId()));
        assertThat(publication.getLinks().get(0).getEntity(),
                is(publicationEntity.getLinks().get(0).getEntity()));
    }

}
